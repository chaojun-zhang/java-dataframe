package cj.dataframe.schema;

import com.google.common.collect.Ordering;
import io.vavr.control.Option;

public final class NullType implements PrimitiveType {
    @Override
    public Option<Ordering> ordering() {
        return Option.none();
    }

}
