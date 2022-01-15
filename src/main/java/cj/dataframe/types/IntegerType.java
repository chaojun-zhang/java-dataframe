package cj.dataframe.types;

import com.google.common.collect.Ordering;

public final class IntegerType implements NumericType<Integer> {
    IntegerType(){}

    @Override
    public Ordering ordering() {
        return  Ordering.from(Integer::compareTo);
    }


    @Override
    public Numeric numeric() {
        return new Numeric<Integer>() {
            @Override
            public Integer plus(Integer left, Integer right) {
                if (left == null) {
                    return right;
                } else if (right == null) {
                    return left;
                }
                return left + right;
            }

            @Override
            public Integer minus(Integer left, Integer right) {
                if (left == null) {
                    return right;
                } else if (right == null) {
                    return left;
                }
                return left - right;
            }

            @Override
            public Integer times(Integer left, Integer right) {
                return left * right;
            }

            @Override
            public Integer divide(Integer left, Integer right) {
                return left / right;
            }

            @Override
            public Integer max(Integer left, Integer right) {
                return Integer.max(left, right);
            }

            @Override
            public Integer min(Integer left, Integer right) {
                return Integer.min(left, right);
            }
            @Override
            public Integer abs(Integer value) {
                return Math.abs(value);
            }

            @Override
            public Integer negate(Integer value) {
                return -value;
            }

            @Override
            public Integer rem(Integer left, Integer right) {
                return left % right;
            }
        };
    }

    @Override
    public Integer initValue() {
        return 0;
    }

}