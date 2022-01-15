package cj.dataframe.expression.named;

import cj.dataframe.exception.ExpressionResolvedException;
import cj.dataframe.expression.LeafExpression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;

public class Star implements LeafExpression,NamedExpression{
    @Override
    public Object evaluate(Row input) {
        throw new ExpressionResolvedException(this);
    }

    @Override
    public DataType dataType() {
        throw new ExpressionResolvedException(this);
    }


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
}
