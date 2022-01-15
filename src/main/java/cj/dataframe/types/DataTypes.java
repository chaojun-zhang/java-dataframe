package cj.dataframe.types;

import java.sql.Timestamp;
import java.util.Date;

public class DataTypes {
    public static final ShortType SHORT = new ShortType();
    public static final IntegerType INT = new IntegerType();
    public static final LongType LONG = new LongType();
    public static final BooleanType BOOLEAN = new BooleanType();
    public static final FloatType FLOAT = new FloatType();
    public static final DoubleType DOUBLE = new DoubleType();
    public static final StringType STRING = new StringType();
    public static final TimestampType TIMESTAMP = new TimestampType();
    public static final DateType DATE = new DateType();


    public static DataType fromTypeString(String type) {
        return switch (type.toLowerCase()) {
            case "int", "integer" -> DataTypes.INT;
            case "long", "bigint" -> DataTypes.LONG;
            case "date" -> DataTypes.DATE;
            case "timestamp" -> DataTypes.TIMESTAMP;
            case "bool", "boolean" -> DataTypes.BOOLEAN;
            case "short" -> DataTypes.SHORT;
            case "float" -> DataTypes.FLOAT;
            case "double" -> DataTypes.DOUBLE;
            default -> DataTypes.STRING;
        };
    }

    public static DataType fromValue(Object value) {
        return switch (value) {
            case Boolean ignored -> BOOLEAN;
            case Short ignored -> SHORT;
            case Integer ignored -> INT;
            case Long ignored -> LONG;
            case Float ignored -> FLOAT;
            case Double ignored -> DOUBLE;
            case Timestamp ignored -> TIMESTAMP;
            case Date ignored -> DATE;
            case String ignored -> STRING;
            default -> throw new IllegalArgumentException("unexpected value:" + value);
        };
    }


}