package cj.dataframe.expression.aggregator;

import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import cj.dataframe.types.DataTypes;

public class Count  implements AggregatedExpression {

    private long value;

    public Expression expression;

    public Count(Expression expression){
        this.expression = expression;
    }

    @Override
    public void merge(Row row) {
        this.value++;
    }

    @Override
    public Object finish() {
        return this.value;
    }

    @Override
    public DataType dataType() {
        return DataTypes.LONG;
    }

    @Override
    public Expression child() {
        return expression;
    }



}