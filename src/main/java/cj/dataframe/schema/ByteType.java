package cj.dataframe.schema;

import com.google.common.collect.Ordering;
import io.vavr.control.Option;

public final class ByteType implements NumericType {
    @Override
    public Option<Ordering> ordering() {
        return Option.of(Ordering.from(Byte::compareTo));
    }

    @Override
    public Numeric<Byte> numeric() {
        return new Numeric<>() {
            @Override
            public Byte plus(Byte left, Byte right) {
                return (byte) (left + right);
            }

            @Override
            public Byte minus(Byte left, Byte right) {
                return (byte) (left - right);
            }

            @Override
            public Byte times(Byte left, Byte right) {
                return (byte) (left * right);
            }

            @Override
            public Byte divide(Byte left, Byte right) {
                return (byte) (left / right);
            }

            @Override
            public Byte max(Byte left, Byte right) {
                return (byte) (left / right);
            }

            @Override
            public Byte min(Byte left, Byte right) {
                return (byte) Math.min(left, right);
            }

            @Override
            public Byte abs(Byte value) {
                return (byte) Math.abs(value);
            }

            @Override
            public Byte negate(Byte value) {
                return (byte) -value;
            }

            @Override
            public Byte rem(Byte left, Byte right) {
                return (byte) (left % right);
            }
        };
    }

    @Override
    public Byte initValue() {
        return 0;
    }
}
