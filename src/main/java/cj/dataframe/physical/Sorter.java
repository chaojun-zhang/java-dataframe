package cj.dataframe.physical;

import cj.dataframe.RowOrdering;
import cj.dataframe.api.DataFrame;
import cj.dataframe.expression.logical.comparision.SortOrder;
import cj.dataframe.expression.named.Attribute;
import cj.dataframe.row.Row;
import io.vavr.collection.Iterator;
import io.vavr.collection.Seq;

import java.util.ArrayList;
import java.util.List;

public final class Sorter implements DataFrame {

    private final DataFrame input;
    private final RowOrdering ordering;

    public Sorter(DataFrame input, Seq<SortOrder> sortOrders) {
        this.input = input;
        this.ordering = new RowOrdering(input.output(), sortOrders);
    }

    @Override
    public Iterator<Row> iterator() {
        final List<Row> rows = new ArrayList<>();
        input.forEach(rows::add);
        rows.sort(ordering);
        return Iterator.ofAll(rows);
    }

    @Override
    public Seq<Attribute> output() {
        return input.output();
    }

}
