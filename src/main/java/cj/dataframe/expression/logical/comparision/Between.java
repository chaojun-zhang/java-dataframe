package cj.dataframe.expression.logical.comparision;

import cj.dataframe.expression.Expression;
import cj.dataframe.expression.logical.And;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import io.vavr.collection.Seq;

public record Between(Expression test,Expression left, Expression right) implements Expression {

    @Override
    public Object evaluate(Row input) {
        return new And(new Gte(test, left), new Lte(test, right)).evaluate(input);
    }

    @Override
    public DataType dataType() {
        return test.dataType();
    }

    @Override
    public Seq<Expression> children() {
        return null;
    }

}
