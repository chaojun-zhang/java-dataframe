package cj.dataframe.physical;

import cj.dataframe.api.DataFrame;
import cj.dataframe.expression.Expression;
import cj.dataframe.expression.named.Attribute;
import cj.dataframe.row.Row;
import io.vavr.collection.Iterator;
import io.vavr.collection.Seq;

public record Filter(DataFrame input, Expression filter) implements DataFrame {

    @Override
    public Iterator<Row> iterator() {
        Iterator<Row> iterator = input.iterator();
        if (filter == null) {
            return iterator;
        } else {
            return iterator.filter(it -> (boolean) filter.evaluate(it));
        }
    }

    @Override
    public Seq<Attribute> output() {
        return input.output();
    }


}
