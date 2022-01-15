package cj.dataframe.types;

import com.google.common.collect.Ordering;

public final class ShortType implements NumericType<Short> {

    ShortType(){}

    @Override
    public Ordering ordering() {
        return Ordering.from(Short::compareTo);
    }


    @Override
    public Numeric<Short> numeric() {
        return new Numeric<>() {
            @Override
            public Short plus(Short left, Short right) {
                return ((Integer) (left + right)).shortValue();
            }

            @Override
            public Short minus(Short left, Short right) {
                return ((Integer) (left - right)).shortValue();
            }

            @Override
            public Short times(Short left, Short right) {
                return ((Integer) (left * right)).shortValue();
            }

            @Override
            public Short divide(Short left, Short right) {
                return ((Integer) (left / right)).shortValue();
            }

            @Override
            public Short max(Short left, Short right) {
                return left > right ? left : right;
            }

            @Override
            public Short min(Short left, Short right) {
                return left < right ? left : right;
            }

            @Override
            public Short abs(Short value) {
                Integer a = value < 0 ? -value : value;
                return a.shortValue();
            }

            @Override
            public Short negate(Short value) {
                return ((Integer) (0 - value)).shortValue();
            }

            @Override
            public Short rem(Short left, Short right) {
                return ((Integer) Math.floorMod(left, right)).shortValue();
            }
        };
    }

    @Override
    public Short initValue() {
        return 0;
    }

}