package cj.dataframe.expression.logical;

import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

public record If(Expression test,
                 Expression yes,
                 Expression no) implements Expression {

    @Override
    public Seq<Object> productElements() {
        return List.of(test, yes, no);
    }

    @Override
    public Object evaluate(Row input) {
        Object evaluate = test.evaluate(input);
        if (evaluate == null) {
            return null;
        } else {
            boolean trueOrFalse = (boolean) evaluate;
            return trueOrFalse ? yes.evaluate(input) : no.evaluate(input);
        }
    }

    @Override
    public DataType dataType() {
        return yes.dataType();
    }


    @Override
    public Seq<Expression> children() {
        return List.of(test, yes, no);
    }
}
