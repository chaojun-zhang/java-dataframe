package cj.dataframe.expression.logical;

import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import cj.dataframe.types.DataTypes;
import io.vavr.collection.List;
import io.vavr.collection.Seq;


public record OrList(Seq<Expression> children) implements Expression {

    @Override
    public Seq<Object> productElements() {
        return List.of(children);
    }

    @Override
    public Object evaluate(Row input) {
        return children.map(it -> it.evaluate(input)).map(it -> {
            if (it == null) {
                return false;
            } else {
                return (boolean) it;
            }
        }).foldLeft(false, (a, b) -> a || b);
    }

    @Override
    public DataType dataType() {
        return DataTypes.BOOLEAN;
    }

}
