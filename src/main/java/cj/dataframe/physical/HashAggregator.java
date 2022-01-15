package cj.dataframe.physical;

import cj.dataframe.api.DataFrame;
import cj.dataframe.expression.aggregator.AggregatedExpression;
import cj.dataframe.expression.named.Attribute;
import cj.dataframe.expression.named.NamedExpression;
import cj.dataframe.row.JoinedRow;
import cj.dataframe.row.Row;
import cj.dataframe.row.RowFactory;
import io.vavr.collection.Iterator;
import io.vavr.collection.Seq;
import lombok.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class HashAggregator {
    private final Seq<NamedExpression> grouping;
    private final Function<Row, Accumulation> metricMapping;
    private final Map<Row, Accumulation> dimensionToState = new HashMap<>();

    @Builder
    public HashAggregator(Seq<NamedExpression> grouping,
                          Seq<AggregatedExpression> aggregators) {
        this.metricMapping = (row) -> new Accumulation(aggregators);
        this.grouping = grouping;
    }

    public void merge(Row row) {
        Row groupingRow = createGrouping(grouping, row);
        Accumulation accumulation = dimensionToState.computeIfAbsent(groupingRow, metricMapping);
        accumulation.merge(row);
    }


    private static Row createGrouping(Seq<NamedExpression> expressions, Row source) {
        return RowFactory.create(expressions.map(it -> it.evaluate(source)).toJavaArray());
    }

    private static class Accumulation {

        private final Seq<AggregatedExpression> aggregators;

        private Accumulation(Seq<AggregatedExpression> aggregatorExpressions) {
            this.aggregators = aggregatorExpressions;
        }

        public Accumulation merge(Row row) {
            aggregators.forEach(aggregator -> aggregator.merge(row));
            return this;
        }

        public Row result() {
            Object[] row = aggregators.map(AggregatedExpression::finish).toJavaArray();
            return RowFactory.create(row);
        }
    }

    public DataFrame toDataFrame() {
        return new DataFrame() {
            @Override
            public Iterator<Row> iterator() {
                final List<Row> aggregators = new ArrayList<>(dimensionToState.size());
                dimensionToState.forEach((dimension, metric) -> {
                    JoinedRow joinedRow = new JoinedRow(dimension, metric.result());
                    aggregators.add(joinedRow);

                });
                return Iterator.ofAll(aggregators);
            }

            @Override
            public Seq<Attribute> output() {
                return null;
            }
        };
    }
}
