package cj.dataframe.row;

import cj.dataframe.expression.Expression;

import java.util.List;

public class RowFactory {
    public static Row create(Object... values) {
        return new GenericRow(values);
    }


    public static Row create(List<Expression> expressions, Row source) {
        return create(expressions.stream().map(it -> it.evaluate(source)).toArray());
    }

}
