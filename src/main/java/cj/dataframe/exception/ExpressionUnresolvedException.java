package cj.dataframe.exception;

import cj.dataframe.expression.Expression;

public class ExpressionUnresolvedException extends RuntimeException{

    public ExpressionUnresolvedException(Expression expression) {
        super(String.format("Expression " + expression + " is unresolved"));

    }

}
