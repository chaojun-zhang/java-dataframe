package cj.dataframe.expression.aggregator;

import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;

public class Max implements AggregatedExpression {
    private Object max;
    private final Expression expression;

    public Max(Expression expression) {
        this.expression = expression;
    }


    @Override
    public void merge(Row row) {
        Object evaluated = expression.evaluate(row);
        int res = expression.dataType().ordering().compare(evaluated, max);
        if (res >= 0) {
            this.max = evaluated;
        }
    }


    @Override
    public Object finish() {
        return this.max;

    }

    @Override
    public DataType dataType() {
        return expression.dataType();
    }

    @Override
    public Expression child() {
        return expression;
    }
}