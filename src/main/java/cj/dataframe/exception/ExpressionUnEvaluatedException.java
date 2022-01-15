package cj.dataframe.exception;

import cj.dataframe.expression.Expression;

public class ExpressionUnEvaluatedException extends RuntimeException{

    public ExpressionUnEvaluatedException(Expression expression) {
        super(String.format("Expression " + expression + " is unEvaluated"));

    }

}
