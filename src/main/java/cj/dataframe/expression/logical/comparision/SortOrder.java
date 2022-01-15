package cj.dataframe.expression.logical.comparision;

import cj.dataframe.expression.Expression;
import cj.dataframe.expression.UnaryExpression;

public record SortOrder(Expression child, SortDirection direction, boolean nullsLarger) implements UnaryExpression {


    public SortOrder(Expression child,
                     SortDirection direction) {
        this(child, direction, true);
    }

    @Override
    public boolean isNullable() {
        return child.isNullable();
    }

    @Override
    public Expression child() {
        return child;
    }

    @Override
    public Object nullSafeEvaluate(Object value) {
        throw new UnsupportedOperationException();
    }


}
