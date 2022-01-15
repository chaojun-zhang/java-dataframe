package cj.dataframe.types;


import io.vavr.control.Option;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static cj.dataframe.types.DataTypes.*;

public final class Castable {
    public static final Castable INSTANCE = new Castable();

    private Castable() {
    }

    private static final Set<String> BOOLEAN_TRUE_STRINGS = Set.of("true", "y", "yes", "t");

    private static final Function<Object, Object> anyToBoolean = x -> switch (x) {
        case Boolean it -> it;
        case Byte it -> it != 0;
        case Short it -> it != 0;
        case Integer it -> it != 0;
        case Long it -> it != 0;
        case Float it -> it != 0;
        case Double it -> it != 0;
        case BigDecimal it -> it.intValue() != 0;
        case String it -> BOOLEAN_TRUE_STRINGS.contains(it.toLowerCase());
        default -> throw new UnsupportedOperationException("Unexpected value: " + x);
    };

    private static final Function<Object, Object> anyToByte = x -> switch (x) {
        case Byte it -> it;
        case Short it -> it.byteValue();
        case Integer it -> it.byteValue();
        case Long it -> it.byteValue();
        case Float it -> it.byteValue();
        case Double it -> it.byteValue();
        case BigDecimal it -> it.byteValue();
        case String it -> Byte.parseByte(it);
        case Boolean it -> (byte) (it ? 1 : 0);
        default -> throw new UnsupportedOperationException("Unexpected value: " + x);
    };

    private static final Function<Object, Object> anyToShort = x -> switch (x) {
        case Byte it -> it.shortValue();
        case Short it -> it;
        case Integer it -> it.shortValue();
        case Long it -> it.shortValue();
        case Float it -> it.shortValue();
        case Double it -> it.shortValue();
        case BigDecimal it -> it.shortValue();
        case String it -> Short.parseShort(it);
        case Boolean it -> (short) (it ? 1 : 0);
        default -> throw new UnsupportedOperationException("Unexpected value: " + x);
    };

    private static final Function<Object, Object> anyToInt = x -> switch (x) {
        case Byte it -> it.intValue();
        case Short it -> it.intValue();
        case Integer it -> it;
        case Long it -> it.intValue();
        case Float it -> it.intValue();
        case Double it -> it.intValue();
        case BigDecimal it -> it.intValue();
        case String it -> Integer.parseInt(it);
        case Boolean it -> it ? 1 : 0;
        default -> throw new UnsupportedOperationException("Unexpected value: " + x);
    };

    private static final Function<Object, Object> anyToLong = x -> switch (x) {
        case Byte it -> it.longValue();
        case Short it -> it.longValue();
        case Integer it -> it.longValue();
        case Long it -> it;
        case Float it -> it.longValue();
        case Double it -> it.longValue();
        case BigDecimal it -> it.longValue();
        case String it -> Long.parseLong(it);
        case Boolean it -> it ? 1L : 0L;
        default -> throw new UnsupportedOperationException("Unexpected value: " + x);
    };


    private static final Function<Object, Object> anyToFloat = x -> switch (x) {
        case Byte it -> it.floatValue();
        case Short it -> it.floatValue();
        case Integer it -> it.floatValue();
        case Long it -> it.floatValue();
        case Float it -> it;
        case Double it -> it.floatValue();
        case BigDecimal it -> it.floatValue();
        case String it -> Float.parseFloat(it);
        case Boolean it -> it ? 1f : 0f;
        default -> throw new UnsupportedOperationException("Unexpected value: " + x);
    };

    private static final Function<Object, Object> anyToDouble = x -> switch (x) {
        case Byte it -> it.doubleValue();
        case Short it -> it.doubleValue();
        case Integer it -> it.doubleValue();
        case Long it -> it.doubleValue();
        case Float it -> it.doubleValue();
        case Double it -> it;
        case BigDecimal it -> it.doubleValue();
        case String it -> Double.parseDouble(it);
        case Boolean it -> it ? 1d : 0d;
        default -> throw new UnsupportedOperationException("Unexpected value: " + x);
    };

    private static final Function<Object, Object> anyToDecimal = x -> switch (x) {
        case Byte it -> BigDecimal.valueOf(it);
        case Short it -> BigDecimal.valueOf(it);
        case Integer it -> BigDecimal.valueOf(it);
        case Long it -> BigDecimal.valueOf(it);
        case Float it -> BigDecimal.valueOf(it);
        case Double it -> BigDecimal.valueOf(it);
        case BigDecimal it -> it;
        case String it -> new BigDecimal(it);
        case Boolean it -> it ? BigDecimal.ONE : BigDecimal.ZERO;
        default -> throw new UnsupportedOperationException("Unexpected value: " + x);
    };

    private static final Function<Object, Object> anyToTimestamp = x -> switch (x) {
        case Timestamp it -> it;
        case Date it -> Timestamp.from(Instant.ofEpochMilli(it.getTime()));
        case Long it -> Timestamp.from(Instant.ofEpochMilli(it));
        case String it -> Timestamp.valueOf(it);
        default -> throw new UnsupportedOperationException("Unexpected value: " + x);
    };

    private static final Function<Object, Object> anyToDate = x -> switch (x) {
        case Long it -> new Date(it);
        case Date it -> it;
        default -> throw new UnsupportedOperationException("Unexpected value: " + x);
    };

    private static final Function<Object, Object> anyToString = Object::toString;

    private static final Map<DataType, Map<DataType, Function<Object, Object>>> toString = Map.of(STRING, Map.of(

            SHORT, anyToString,
            INT, anyToString,
            LONG, anyToString,
            FLOAT, anyToString,
            DOUBLE, anyToString,
            BOOLEAN, anyToString));

    private static final Map<DataType, Map<DataType, Function<Object, Object>>> CAST_BUILDER = Map.of(
            BOOLEAN, Map.of(
                    SHORT, anyToBoolean,
                    INT, anyToBoolean,
                    LONG, anyToBoolean,
                    FLOAT, anyToBoolean,
                    DOUBLE, anyToBoolean,
                    STRING, anyToBoolean
            ),  SHORT, Map.of(
                    INT, anyToShort,
                    LONG, anyToShort,
                    FLOAT, anyToShort,
                    DOUBLE, anyToShort,
                    STRING, anyToShort,
                    BOOLEAN, anyToShort
            ), INT, Map.of(
                    SHORT, anyToInt,
                    LONG, anyToInt,
                    FLOAT, anyToInt,
                    DOUBLE, anyToInt,
                    STRING, anyToInt,
                    BOOLEAN, anyToInt
            ), LONG, Map.of(
                    SHORT, anyToLong,
                    INT, anyToLong,
                    FLOAT, anyToLong,
                    DOUBLE, anyToLong,
                    STRING, anyToLong,
                    BOOLEAN, anyToLong
            ), FLOAT, Map.of(
                    SHORT, anyToFloat,
                    INT, anyToFloat,
                    LONG, anyToFloat,
                    DOUBLE, anyToFloat,
                    STRING, anyToFloat,
                    BOOLEAN, anyToFloat
            ), DOUBLE, Map.of(
                    SHORT, anyToDouble,
                    INT, anyToDouble,
                    LONG, anyToDouble,
                    FLOAT, anyToDouble,
                    STRING, anyToDouble,
                    BOOLEAN, anyToDouble
            ), DataTypes.DATE, Map.of(
                    LONG, anyToDate
            ), DataTypes.TIMESTAMP, Map.of(
                    LONG, anyToInt
            )
    );


    public boolean castable(DataType left, DataType right) {
        if (left.getClass() == right.getClass()) {
            return true;
        }

        if (CAST_BUILDER.containsKey(right)) {
            return CAST_BUILDER.get(right).containsKey(left);
        }
        return false;
    }


    public Option<Function<Object, Object>> caster(DataType left, DataType right) {
        if (left.getClass() == right.getClass()) {
            return Option.some(Function.identity());
        }
        if (CAST_BUILDER.containsKey(right)) {
            return Option.some(CAST_BUILDER.get(right).get(left));
        } else {
            return Option.none();
        }
    }


}
