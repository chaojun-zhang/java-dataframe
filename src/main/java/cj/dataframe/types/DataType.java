package cj.dataframe.types;

import com.google.common.collect.Ordering;

import java.util.Date;

public sealed interface DataType permits PrimitiveType {

    Ordering ordering();

    /**
     * check current datatype can be cast to that datatype
     */
    default boolean isCastableTo(DataType that) {
        return Castable.INSTANCE.castable(this, that);
    }


    default <T extends DataType> T as() {
        return (T) this;
    }

    default Class underlyingClass() {
        return switch (this) {
            case BooleanType ignored -> boolean.class;
            case IntegerType ignored -> int.class;
            case ShortType ignored -> short.class;
            case LongType ignored -> long.class;
            case FloatType ignored -> float.class;
            case DoubleType ignored -> double.class;
            case DateType ignored -> Date.class;
            case TimestampType ignored -> long.class;
            case StringType ignored -> String.class;
        };
    }
}