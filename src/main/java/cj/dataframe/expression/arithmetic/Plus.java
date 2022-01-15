package cj.dataframe.expression.arithmetic;

import cj.dataframe.expression.Expression;
import cj.dataframe.types.DataType;

public record Plus(Expression left, Expression right) implements BinaryArithmetic {

    @Override
    public Object nullSafeEvaluate(Object leftValue, Object rightValue) {
        return numeric().plus(leftValue, rightValue);
    }

    @Override
    public DataType dataType() {
        return left.dataType();
    }
}
