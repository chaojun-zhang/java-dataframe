package cj.dataframe.expression.arithmetic;

import cj.dataframe.expression.Expression;
import cj.dataframe.types.DataType;

public record Positive(Expression child) implements UnaryArithmetic {

    @Override
    public DataType dataType() {
        return child.dataType();
    }

    @Override
    public Object nullSafeEvaluate(Object value) {
        return value;
    }
}
