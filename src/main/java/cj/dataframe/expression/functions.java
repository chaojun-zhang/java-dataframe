package cj.dataframe.expression;

import cj.dataframe.api.Column;
import cj.dataframe.expression.logical.If;
import cj.dataframe.types.DataTypes;

public abstract class functions {

    private functions() {
    }

    //literal functions
    public static Column lit(Object that) {
        return typedLit(that);
    }

    public static Column typedLit(Object that) {
        if (that instanceof Column) {
            return (Column) that;
        } else {
            return new Column(new Literal(that, DataTypes.fromValue(that)));
        }
    }

    //logical functions
    public static Column If(Column test, Column yes, Column no) {
        return new Column(new If(test.getExpr(), yes.getExpr(), no.getExpr()));
    }
}
