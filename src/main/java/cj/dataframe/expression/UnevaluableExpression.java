package cj.dataframe.expression;

import cj.dataframe.row.Row;

public interface UnevaluableExpression extends Expression {

    @Override
    default Object evaluate(Row input) {
        throw new UnsupportedOperationException();
    }
}
