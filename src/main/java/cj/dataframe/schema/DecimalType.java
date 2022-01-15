package cj.dataframe.schema;

import com.google.common.collect.Ordering;
import io.vavr.control.Option;

import java.math.BigDecimal;

public final class DecimalType implements NumericType<BigDecimal> {
    DecimalType(){}

    @Override
    public Option<Ordering> ordering() {
        return Option.of(Ordering.from(BigDecimal::compareTo));
    }



    @Override
    public Numeric numeric() {
        return new Numeric<BigDecimal>() {

            @Override
            public BigDecimal plus(BigDecimal left, BigDecimal right) {
                return left.add(right);
            }

            @Override
            public BigDecimal minus(BigDecimal left, BigDecimal right) {
                return left.subtract(right);
            }

            @Override
            public BigDecimal times(BigDecimal left, BigDecimal right) {
                return left.multiply(right);
            }

            @Override
            public BigDecimal divide(BigDecimal left, BigDecimal right) {
                return left.divide(right);
            }

            @Override
            public BigDecimal max(BigDecimal left, BigDecimal right) {
                return left.max(right);
            }

            @Override
            public BigDecimal min(BigDecimal left, BigDecimal right) {
                return left.min(right);
            }

            @Override
            public BigDecimal abs(BigDecimal value) {
                return value.abs();
            }

            @Override
            public BigDecimal negate(BigDecimal value) {
                return value.negate();
            }

            @Override
            public BigDecimal rem(BigDecimal left, BigDecimal right) {
                return left.remainder(right);
            }


        };
    }

    @Override
    public BigDecimal initValue() {
        return BigDecimal.ZERO;
    }


}
