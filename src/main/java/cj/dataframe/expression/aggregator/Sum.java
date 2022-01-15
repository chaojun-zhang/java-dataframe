package cj.dataframe.expression.aggregator;

import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import cj.dataframe.types.NumericType;


public class Sum implements AggregatedExpression {

    private Object value;

    private final Expression child;

    public Sum(Expression child) {
        this.child = child;
    }


    @Override
    public void merge(Row row) {
        Object thatValue = child.evaluate(row);
        if (this.value == null) {
            this.value = thatValue;
        } else {
            NumericType dataType = dataType().as();
            this.value = dataType.numeric().plus(value, thatValue);
        }
    }

    @Override
    public Object finish() {
        if (this.value == null) {
            return 0;
        }
        return this.value;
    }

    @Override
    public DataType dataType() {
        return child.dataType();
    }

    @Override
    public Expression child() {
        return child;
    }
}