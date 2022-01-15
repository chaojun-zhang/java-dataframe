package cj.dataframe.types;

import com.google.common.collect.Ordering;

import java.util.Date;

public final class DateType implements PrimitiveType {
    DateType(){}

    @Override
    public Ordering ordering() {
        return Ordering.from(Date::compareTo);
    }


}
