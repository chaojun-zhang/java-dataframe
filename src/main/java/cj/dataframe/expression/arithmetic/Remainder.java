package cj.dataframe.expression.arithmetic;

import cj.dataframe.expression.Expression;
import cj.dataframe.types.NumericType;

public record Remainder(Expression left, Expression right) implements BinaryArithmetic {

    @Override
    public Object nullSafeEvaluate(Object leftValue, Object rightValue) {
        NumericType as = dataType().as();
        return as.numeric().rem(leftValue, rightValue);
    }


}
