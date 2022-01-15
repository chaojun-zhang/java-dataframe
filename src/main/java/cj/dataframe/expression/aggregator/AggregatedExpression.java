package cj.dataframe.expression.aggregator;

import cj.dataframe.expression.UnaryExpression;
import cj.dataframe.row.Row;

public interface AggregatedExpression extends UnaryExpression {

    void merge(Row row);

    Object finish();

    default Object nullSafeEvaluate(Object value){
        throw new UnsupportedOperationException();
    }
}