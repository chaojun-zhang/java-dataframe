package cj.dataframe.schema;

import com.google.common.collect.Ordering;
import io.vavr.control.Option;

public final class ShortType implements NumericType<Short> {

    ShortType(){}

    @Override
    public Option<Ordering> ordering() {
        return Option.of(Ordering.from(Short::compareTo));
    }


    @Override
    public Numeric<Short> numeric() {
        return null;
    }

    @Override
    public Short initValue() {
        return 0;
    }

}