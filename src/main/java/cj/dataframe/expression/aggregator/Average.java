package cj.dataframe.expression.aggregator;

import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import cj.dataframe.types.NumericType;

public class Average implements AggregatedExpression {

    private Object value;

    private Expression child;

    private long count;

    public Average(Expression child) {
        this.child = child;
    }

    @Override
    public void merge(Row row) {
        Object number = child.evaluate(row);
        if (this.value == null) {
            this.value = number;
        } else {
            NumericType dataType = dataType().as();
            this.value = dataType.numeric().plus(value, number);
        }
        this.count++;
    }

    @Override
    public Object finish() {
        if (this.value == null) {
            return 0d;
        }
        NumericType dataType = dataType().as();
        return dataType.numeric().divide(this.value, count);
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