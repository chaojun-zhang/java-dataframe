package cj.dataframe.expression.named;

import cj.dataframe.expression.LeafExpression;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

public interface Attribute extends NamedExpression, LeafExpression {

    @Override
    default Attribute attribute() {
        return this;
    }

    @Override
    default Seq<Attribute> references() {
        return List.of(this);
    }

    @Override
    default boolean isBound() {
        return false;
    }
}
