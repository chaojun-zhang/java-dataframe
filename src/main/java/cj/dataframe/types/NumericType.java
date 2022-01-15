package cj.dataframe.types;

public sealed interface NumericType<T extends Number> extends PrimitiveType permits  DoubleType, FloatType, IntegerType, LongType, ShortType  {

    Numeric<T> numeric();

    T initValue();
}
