package cj.dataframe.exception;

import cj.dataframe.expression.Expression;

public class NameResolvedException extends RuntimeException {

    public NameResolvedException(Expression expression) {
        super("The name of expression " + expression + " is unresolved");

    }
}
