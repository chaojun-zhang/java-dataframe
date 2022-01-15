package cj.dataframe.physical;

import cj.dataframe.api.Column;
import cj.dataframe.api.DataFrame;
import cj.dataframe.expression.aggregator.AggregatedExpression;
import cj.dataframe.expression.named.NamedExpression;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

import java.util.Arrays;
import java.util.Objects;

public record Grouped(DataFrame child,
                      Seq<NamedExpression> grouping) {

    public Grouped(DataFrame child, Seq<NamedExpression> grouping) {
        this.child = Objects.requireNonNull(child);
        this.grouping = Objects.requireNonNull(grouping);
    }

    public DataFrame agg(Seq<AggregatedExpression> aggregators) {
        HashAggregator aggregator = HashAggregator.builder()
                .grouping(grouping)
                .aggregators(aggregators)
                .build();
        child.forEach(aggregator::merge);

        return aggregator.toDataFrame();
    }

    public DataFrame agg(Column... columns) {
        Seq<AggregatedExpression> aggregators = List.ofAll(Arrays.stream(columns)
                .map(Column::aggregated)
                .filter(Option::isDefined)
                .map(Option::get));
        HashAggregator aggregator = HashAggregator.builder()
                .grouping(grouping)
                .aggregators(aggregators)
                .build();
        child.forEach(aggregator::merge);

        return aggregator.toDataFrame();
    }


}
