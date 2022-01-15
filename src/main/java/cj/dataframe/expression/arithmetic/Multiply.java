package cj.dataframe.expression.arithmetic;

import cj.dataframe.expression.Expression;
import cj.dataframe.types.DataType;

public record Multiply(Expression left, Expression right) implements BinaryArithmetic {

    @Override
    public Object nullSafeEvaluate(Object leftValue, Object rightValue) {
        return numeric().times(leftValue, rightValue);
    }

    @Override
    public DataType dataType() {
        return left.dataType();
    }
}
