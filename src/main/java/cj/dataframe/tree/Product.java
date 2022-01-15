package cj.dataframe.tree;

import io.vavr.collection.Seq;

public interface Product {

    Seq<Object> productElements();

    default int arity(){
        return productElements().size();
    }
}
