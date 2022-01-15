package cj.dataframe.expression.aggregator;

import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;

public class Min implements AggregatedExpression {
    private Object min;
    private final Expression expression;

    public Min(Expression expression) {
        this.expression = expression;
    }


    @Override
    public void merge(Row row) {
        Object evaluated = expression.evaluate(row);
        int res = expression.dataType().ordering().compare(evaluated, min);
        if (res <= 0) {
            this.min = evaluated;
        }
    }

    @Override
    public Object finish() {
        return this.min;
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