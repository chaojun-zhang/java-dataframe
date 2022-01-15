package cj.dataframe.expression.aggregator;

import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;

public class Last implements AggregatedExpression {

    private Object value;

    private final Expression expression;

    public Last(Expression expression) {
        this.expression = expression;
    }


    @Override
    public void merge(Row row) {
        this.value = expression.evaluate(row);
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