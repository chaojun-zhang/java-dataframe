package cj.dataframe.expression.logical;

import cj.dataframe.expression.Expression;
import cj.dataframe.types.DataType;
import cj.dataframe.types.DataTypes;


public record Not(Expression child) implements UnaryLogicalPredicate {

    @Override
    public DataType dataType() {
        return DataTypes.BOOLEAN;
    }

    @Override
    public Object nullSafeEvaluate(Object value) {
        return !(Boolean) value;
    }
}
