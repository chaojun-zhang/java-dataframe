package cj.dataframe.types;

import com.google.common.collect.Ordering;

public final class BooleanType implements PrimitiveType {
    BooleanType() {
    }

    @Override
    public Ordering ordering() {
        return  Ordering.from(Boolean::compareTo);
    }



}
