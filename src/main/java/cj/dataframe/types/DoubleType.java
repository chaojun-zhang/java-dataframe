package cj.dataframe.types;

import com.google.common.collect.Ordering;

public final class DoubleType implements NumericType<Double> {
    DoubleType(){}

    @Override
    public Ordering ordering() {
        return  Ordering.from(Double::compareTo);
    }


    @Override
    public Numeric<Double> numeric() {
        return new Numeric<Double>() {
            @Override
            public Double plus(Double left, Double right) {
                if (left == null) {
                    return right;
                } else if (right == null) {
                    return left;
                }
                return left + right;
            }

            @Override
            public Double minus(Double left, Double right) {

                if (left == null) {
                    return right;
                } else if (right == null) {
                    return left;
                }
                return left - right;
            }

            @Override
            public Double times(Double left, Double right) {
                return left * right;
            }

            @Override
            public Double divide(Double left, Double right) {
                return left / right;
            }

            @Override
            public Double max(Double left, Double right) {
                return Double.max(left, right);
            }

            @Override
            public Double min(Double left, Double right) {
                return Double.min(left, right);
            }

            @Override
            public Double abs(Double value) {
                return Math.abs(value);
            }

            @Override
            public Double negate(Double value) {
                return 0 - value;
            }

            @Override
            public Double rem(Double left, Double right) {
                return left % right;
            }

        };
    }

    @Override
    public Double initValue() {
        return 0d;
    }

}