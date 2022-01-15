package cj.dataframe.row;

import io.vavr.collection.Seq;

public interface Row extends TypedGetter {

    int size();

    boolean isEmpty();

    Seq<Object> getValues();

}
