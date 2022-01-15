package cj.dataframe.expression.named;

import cj.dataframe.exception.ExpressionResolvedException;
import cj.dataframe.expression.Expression;
import cj.dataframe.expression.UnevaluableExpression;
import cj.dataframe.types.DataType;

public interface UnresolvedExpression extends Expression, UnevaluableExpression {

    @Override
    default boolean isResolved() {
        return false;
    }

    @Override
    default DataType dataType(){
        throw new ExpressionResolvedException(this);
    }

}
