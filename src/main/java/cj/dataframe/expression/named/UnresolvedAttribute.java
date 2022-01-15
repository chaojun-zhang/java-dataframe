package cj.dataframe.expression.named;

import cj.dataframe.exception.ExpressionUnresolvedException;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

public record UnresolvedAttribute(String name) implements Attribute, UnresolvedNamedExpression {

    @Override
    public Seq<Object> productElements() {
        return List.of(name);
    }

    @Override
    public Object evaluate(Row input) {
        throw new UnsupportedOperationException();
    }


    @Override
    public DataType dataType() {
        throw new ExpressionUnresolvedException(this);
    }

    @Override
    public ExpressionId expressionId() {
        throw new ExpressionUnresolvedException(this);
    }

}
