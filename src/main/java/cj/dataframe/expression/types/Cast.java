package cj.dataframe.expression.types;

import cj.dataframe.expression.Expression;
import cj.dataframe.expression.UnaryExpression;
import cj.dataframe.types.Castable;
import cj.dataframe.types.DataType;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

public record Cast(Expression child, DataType dataType) implements UnaryExpression {

    @Override
    public Seq<Object> productElements() {
        return List.of(child, dataType);
    }

    @Override
    public Object nullSafeEvaluate(Object value) {
        DataType fromType = child.dataType();
        if (fromType.getClass() == dataType.getClass()) {
            return value;
        }
        return Castable.INSTANCE.caster(fromType, dataType).get().apply(value);
    }


}
