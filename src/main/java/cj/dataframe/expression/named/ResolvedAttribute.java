package cj.dataframe.expression.named;

import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

public final class ResolvedAttribute implements Attribute, ResolvedExpression {

    private final String name;

    private final DataType dataType;

    private final boolean nullable;

    private final ExpressionId expressionId;

    public ResolvedAttribute(String name, DataType dataType,
                             boolean nullable,
                             ExpressionId expressionId){
        this.name = name;
        this.dataType = dataType;
        this.nullable = nullable;
        this.expressionId = expressionId;
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    public ResolvedAttribute(String name, DataType dataType,boolean nullable){
        this(name, dataType, nullable, ExpressionId.newExpressionId());
    }


    public ResolvedAttribute(String name, DataType dataType){
        this(name, dataType, true, ExpressionId.newExpressionId());
    }

    @Override
    public Seq<Object> productElements() {
        return List.of(name, dataType, nullable);
    }

    @Override
    public Object evaluate(Row input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DataType dataType() {
        return dataType;
    }

    @Override
    public ExpressionId expressionId() {
        return expressionId;
    }

    @Override
    public String name() {
        return name;
    }


}
