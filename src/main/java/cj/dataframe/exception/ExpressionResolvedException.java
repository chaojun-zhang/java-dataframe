package cj.dataframe.exception;

import cj.dataframe.expression.Expression;

public class ExpressionResolvedException extends RuntimeException{

    private final Expression expression;

    public ExpressionResolvedException(Expression expression) {
        super(String.format("expression %s resolved failed", expression));
        this.expression = expression;
    }

}
