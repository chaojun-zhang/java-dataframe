package cj.dataframe.expression.named;

import cj.dataframe.expression.Expression;
import cj.dataframe.expression.UnevaluableExpression;

public interface ResolvedExpression extends Expression, UnevaluableExpression {


    @Override
    default boolean isResolved() {
        return true;
    }

}
