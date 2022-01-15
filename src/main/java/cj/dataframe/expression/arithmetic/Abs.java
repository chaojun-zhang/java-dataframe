package cj.dataframe.expression.arithmetic;

import cj.dataframe.expression.Expression;

public class Abs implements UnaryArithmetic {

    private final Expression expression;

    public Abs(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Expression child() {
        return expression;
    }

    @Override
    public Object nullSafeEvaluate(Object value) {
        return numeric().abs(value);
    }
}
