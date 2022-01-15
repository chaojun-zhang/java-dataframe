package cj.dataframe.types;

import com.google.common.collect.Ordering;

public final class FloatType implements NumericType<Float> {
    FloatType() {
    }

    @Override
    public Ordering ordering() {
        return  Ordering.from(Float::compareTo);
    }


    @Override
    public Numeric numeric() {
        return new Numeric<Float>() {
            @Override
            public Float plus(Float left, Float right) {
                if (left == null) {
                    return right;
                } else if (right == null) {
                    return left;
                }
                return left + right;
            }

            @Override
            public Float minus(Float left, Float right) {

                if (left == null) {
                    return right;
                } else if (right == null) {
                    return left;
                }
                return left - right;
            }

            @Override
            public Float times(Float left, Float right) {
                return left * right;
            }

            @Override
            public Float divide(Float left, Float right) {
                return left / right;
            }

            @Override
            public Float max(Float left, Float right) {
                return Float.max(left, right);
            }

            @Override
            public Float min(Float left, Float right) {
                return Float.min(left, right);
            }

            @Override
            public Float abs(Float value) {
                return Math.abs(value);
            }

            @Override
            public Float negate(Float value) {
                return 0 - value;
            }

            @Override
            public Float rem(Float left, Float right) {
                return left % right;
            }
        };
    }

    @Override
    public Float initValue() {
        return 0f;
    }

}