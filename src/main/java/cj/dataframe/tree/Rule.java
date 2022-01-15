package cj.dataframe.tree;

import io.vavr.PartialFunction;

public interface Rule<T> extends PartialFunction<T,T> {
}
