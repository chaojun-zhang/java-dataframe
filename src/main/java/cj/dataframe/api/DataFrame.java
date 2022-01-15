package cj.dataframe.api;

import cj.dataframe.Tabulate;
import cj.dataframe.expression.aggregator.AggregatedExpression;
import cj.dataframe.expression.logical.comparision.SortDirection;
import cj.dataframe.expression.logical.comparision.SortOrder;
import cj.dataframe.expression.named.Attribute;
import cj.dataframe.expression.named.NamedExpression;
import cj.dataframe.physical.Filter;
import cj.dataframe.physical.Grouped;
import cj.dataframe.physical.Limit;
import cj.dataframe.physical.Project;
import cj.dataframe.physical.Sorter;
import cj.dataframe.row.Row;
import cj.dataframe.types.StructType;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;


public interface DataFrame {

    Iterator<Row> iterator();

    default StructType getSchema() {
        return StructType.fromAttribute(output());
    }

    Seq<Attribute> output();


    default Grouped groupBy(Column... columns) {
        List<NamedExpression> expression = List.of(columns).map(Column::named);
        return new Grouped(this, expression);
    }

    default DataFrame where(Column filter) {
        return new Filter(this, filter.getExpr());
    }

    default DataFrame select(Column... columns) {
        List<NamedExpression> expression = List.of(columns).map(Column::named);
        return new Project(expression, this);
    }

    default DataFrame agg(Column... columns) {
        List<AggregatedExpression> aggregators = List.of(columns)
                .map(Column::aggregated)
                .filter(Option::isDefined)
                .map(Option::get);
        if (aggregators.isEmpty()) {
            throw new IllegalArgumentException("no valid aggregator columns");
        }
        return groupBy().agg(aggregators);
    }

    default Column col(String name) {
        return new Column(name);
    }

    default DataFrame orderBy(Column... columns) {
        List<SortOrder> sortOrders = List.of(columns).map(Column::getExpr)
                .map(it -> switch (it) {
                    case SortOrder a -> a;
                    default -> new SortOrder(it, SortDirection.ASC, true);
                }).toList();
        return new Sorter(this, sortOrders);
    }


    default void forEach(Consumer<Row> consumer) {
        this.iterator().forEach(consumer);
    }

    default long count() {
        AtomicLong size = new AtomicLong(0L);
        this.forEach(it -> size.incrementAndGet());
        return size.get();
    }

    default void show(boolean truncate) {
        new Tabulate(this).show(truncate);
    }

    default DataFrame limit(int limit) {
        return new Limit(this, limit);
    }


}
