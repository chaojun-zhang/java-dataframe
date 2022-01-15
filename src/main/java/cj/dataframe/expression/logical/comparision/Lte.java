package cj.dataframe.expression.logical.comparision;

import cj.dataframe.expression.Expression;

public record Lte(Expression left, Expression right) implements BinaryComparison {

    @Override
    public Object nullSafeEvaluate(Object leftValue, Object rightValue) {
        return ordering().compare(leftValue, rightValue) <= 0;
    }
}
