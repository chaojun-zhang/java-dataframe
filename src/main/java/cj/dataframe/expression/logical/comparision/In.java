package cj.dataframe.expression.logical.comparision;

import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import cj.dataframe.types.DataTypes;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

public record In(Expression test, Seq<Expression> list) implements Expression {

    @Override
    public Object evaluate(Row input) {
        Object testValue = test.evaluate(input);
        Seq<Object> listValues = list.map(it -> it.evaluate(input));
        return listValues.contains(testValue);
    }

    @Override
    public DataType dataType() {
        return DataTypes.BOOLEAN;
    }

    @Override
    public Seq<Expression> children() {
        return List.of(test).appendAll(list);
    }
}
