package cj.dataframe.types;

import com.google.common.collect.Ordering;

public final class TimestampType implements PrimitiveType {

    public TimestampType() {}

    @Override
    public Ordering ordering() {
        return Ordering.from(Long::compareTo);
    }

}
