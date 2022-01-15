package cj.dataframe.expression.aggregator;

import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import com.google.common.collect.MinMaxPriorityQueue;
import lombok.Getter;

public class TopN implements AggregatedExpression {

    private final MinMaxPriorityQueue<Object> priorityQueue;

    @Getter
    private final Expression child;

    public TopN(Expression child, int maximumSize) {
        this.child = child;
        priorityQueue = MinMaxPriorityQueue.orderedBy(child.dataType().ordering())
                .maximumSize(maximumSize)
                .create();
    }

    @Override
    public void merge(Row row) {
        Object evaluated = child.evaluate(row);
        this.priorityQueue.add(evaluated);
    }

    @Override
    public Object finish() {
        return this.priorityQueue.pollLast();
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
