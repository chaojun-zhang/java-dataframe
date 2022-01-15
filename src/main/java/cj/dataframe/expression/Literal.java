package cj.dataframe.expression;

import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import cj.dataframe.types.DataTypes;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import static com.qiniu.defy.statd.dataframe.schema.DataTypes.*;

public final class Literal implements LeafExpression {

    private Object value;
    private DataType dataType;

    public Literal(Object value, DataType dataType){
        this.value = value;
        this.dataType = dataType;
    }

    @Override
    public Seq<Object> productElements() {
        return List.of(value, dataType);
    }

    @Override
    public Object evaluate(Row input) {
        return value;
    }

    @Override
    public DataType dataType() {
        return dataType;
    }

    @Override
    public boolean isNullable() {
        return value == null;
    }

    public static Literal of(Object value) {
        if (value == null) {
            return new Literal(null, DataTypes.NULL);
        }
        return switch (value) {
            case Boolean it -> new Literal(it, BOOLEAN);
            case Byte it -> new Literal(it, BYTE);
            case Short it -> new Literal(it, SHORT);
            case Integer it -> new Literal(it, INT);
            case Long it -> new Literal(it, LONG);
            case Float it -> new Literal(it, FLOAT);
            case Double it -> new Literal(it, DOUBLE);
            case BigDecimal it -> new Literal(it, DECIMAL);
            case String it -> new Literal(it, STRING);
            case Timestamp it -> new Literal(it.getTime(), TIMESTAMP);
            case Date it -> new Literal(it, DATE);
            default -> throw new IllegalArgumentException("unexpected value:" + value);
        };
    }
}
