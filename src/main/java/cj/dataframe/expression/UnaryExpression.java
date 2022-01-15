package cj.dataframe.expression;

import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Option;


public interface UnaryExpression extends Expression {

    Expression child();



    @Override
    default DataType dataType(){
        return child().dataType();
    }

    @Override
    default Seq<Expression> children(){
        return List.of(child());
    }

    Object nullSafeEvaluate(Object value);

    @Override
    default Object evaluate(Row input) {
        return Option.of(child().evaluate(input)).map(this::nullSafeEvaluate).getOrNull();
    }
}
