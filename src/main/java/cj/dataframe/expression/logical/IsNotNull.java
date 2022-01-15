package cj.dataframe.expression.logical;

import cj.dataframe.expression.Expression;
import cj.dataframe.types.DataType;

public record IsNotNull(Expression child) implements UnaryLogicalPredicate {

    @Override
    public Object nullSafeEvaluate(Object value) {
        return value != null;
    }

    @Override
    public DataType dataType() {
        return child.dataType();
    }
}
