package cj.dataframe.tree;

import java.util.function.Function;

public interface PartialFunction<T, R> extends io.vavr.PartialFunction<T, R> {

    default R applyOrElse(T a, Function<T, R> f) {
        if (isDefinedAt(a)) {
            return apply(a);
        } else {
            return f.apply(a);
        }
    }
}
