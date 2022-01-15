package cj.dataframe.types;

import com.google.common.collect.Ordering;

public final class StringType implements PrimitiveType {
    StringType(){}

    @Override
    public Ordering ordering() {
        return  Ordering.from(String::compareTo);
    }


}
