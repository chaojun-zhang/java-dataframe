package cj.dataframe.physical;

import cj.dataframe.api.DataFrame;
import cj.dataframe.expression.named.Attribute;
import cj.dataframe.row.Row;
import io.vavr.collection.Iterator;
import io.vavr.collection.Seq;

public record Limit(DataFrame input, int limit) implements DataFrame {

    @Override
    public Iterator<Row> iterator() {
        return input.iterator().take(limit);
    }


    @Override
    public Seq<Attribute> output() {
        return input.output();
    }
}
