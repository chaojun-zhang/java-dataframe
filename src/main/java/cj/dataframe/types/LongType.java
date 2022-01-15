package cj.dataframe.types;

import com.google.common.collect.Ordering;

public final class LongType implements NumericType<Long> {
    LongType(){}

    @Override
    public Ordering ordering() {
        return Ordering.from(Long::compareTo);
    }

    @Override
    public Numeric<Long> numeric() {
        return new Numeric<Long>() {
            @Override
            public Long plus(Long left, Long right) {
                if (left == null) {
                    return right;
                } else if (right == null) {
                    return left;
                }
                return left + right;
            }

            @Override
            public Long minus(Long left, Long right) {

                if (left == null) {
                    return right;
                } else if (right == null) {
                    return left;
                }
                return left - right;
            }

            @Override
            public Long times(Long left, Long right) {
                return left * right;
            }

            @Override
            public Long divide(Long left, Long right) {
                return left / right;
            }

            @Override
            public Long max(Long left, Long right) {
                return Long.max(left, right);
            }

            @Override
            public Long min(Long left, Long right) {
                return Long.min(left, right);
            }
            @Override
            public Long abs(Long value) {
                return Math.abs(value);
            }

            @Override
            public Long negate(Long value) {
                return -value;
            }

            @Override
            public Long rem(Long left, Long right) {
                return left % right;
            }
        };
    }

    @Override
    public Long initValue() {
        return 0L;
    }

}