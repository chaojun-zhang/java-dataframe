package cj.dataframe.physical;

import cj.dataframe.api.DataFrame;
import cj.dataframe.expression.named.Attribute;
import cj.dataframe.row.Row;
import cj.dataframe.types.StructType;
import io.vavr.collection.Iterator;
import io.vavr.collection.Seq;

public class LocalRelation implements DataFrame {
    public final Seq<Attribute> output;

    private final Iterator<Row> rows;

    public LocalRelation(Seq<Attribute> output, Iterator<Row> rows) {
        this.output = output;
        this.rows = rows;
    }

    public LocalRelation(StructType schema, Iterator<Row> rows) {
        this(schema.toAttributes(), rows);
    }

    @Override
    public Iterator<Row> iterator() {
        return rows;
    }

    @Override
    public Seq<Attribute> output() {
        return output;
    }

}
