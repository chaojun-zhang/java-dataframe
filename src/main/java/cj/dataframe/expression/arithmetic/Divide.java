package cj.dataframe.expression.arithmetic;

import cj.dataframe.expression.Expression;
import cj.dataframe.types.DataType;

public final class Divide implements BinaryArithmetic {

    private final Expression left;
    private final Expression right;

    public Divide(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Object nullSafeEvaluate(Object leftValue, Object rightValue) {
        return numeric().divide(leftValue, rightValue);
    }

    @Override
    public Expression left() {
        return  left;
    }

    @Override
    public Expression right() {
        return right;
    }

    @Override
    public DataType dataType() {
        return left.dataType();
    }
}
