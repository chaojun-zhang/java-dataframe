package cj.dataframe.expression.aggregator;

import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;

public class First implements AggregatedExpression {

    private Object value;

    private final Expression expression;

    public First(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void merge(Row row) {
        if (this.value == null) {
            this.value = expression.evaluate(row);
        }
    }

    @Override
    public Object finish() {
        return this.value;
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