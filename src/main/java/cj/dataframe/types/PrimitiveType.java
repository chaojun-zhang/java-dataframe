package cj.dataframe.types;

public sealed interface PrimitiveType extends DataType permits StringType, BooleanType, DateType, NumericType, TimestampType {
}
