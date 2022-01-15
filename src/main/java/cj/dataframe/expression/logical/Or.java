package cj.dataframe.expression.logical;

import cj.dataframe.expression.Expression;


public record Or(Expression left, Expression right) implements BinaryLogicalPredicate {

    @Override
    public Object nullSafeEvaluate(Object leftValue, Object rightValue) {
        Boolean lhs = (Boolean) leftValue;
        Boolean rhs = (Boolean) rightValue;
        return lhs || rhs;
    }
}
