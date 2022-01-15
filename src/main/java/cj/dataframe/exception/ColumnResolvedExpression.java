package cj.dataframe.exception;


import cj.dataframe.api.Column;

public class ColumnResolvedExpression extends RuntimeException{

    public ColumnResolvedExpression(Column column) {
        super("Column " + column + " is an unresolved column, you must alias it");

    }


}
