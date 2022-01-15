package cj.dataframe.expression.named;

import cj.dataframe.exception.ExpressionResolvedException;
import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

public record UnresolvedAlias(Expression child) implements  Attribute, UnresolvedExpression {

    @Override
    public ExpressionId expressionId() {
        throw new ExpressionResolvedException(this);
    }

    @Override
    public String name() {
        throw new ExpressionResolvedException(this);
    }

    @Override
    public Attribute attribute() {
        throw new ExpressionResolvedException(this);
    }


    @Override
    public Object evaluate(Row input) {
        throw new ExpressionResolvedException(this);
    }

    @Override
    public DataType dataType() {
        throw new ExpressionResolvedException(this);
    }

    @Override
    public Seq<Expression> children() {
        return List.of(child);
    }

}
