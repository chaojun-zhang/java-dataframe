package cj.dataframe.expression.named;

import cj.dataframe.expression.LeafExpression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

public record BoundRef(int ordinal, DataType dataType, boolean nullable) implements LeafExpression {

    @Override
    public Seq<Object> productElements() {
        return List.of(ordinal, dataType, nullable);
    }

    @Override
    public Object evaluate(Row input) {
        return input.get(ordinal);
    }

    @Override
    public boolean isBound() {
        return true;
    }
}
