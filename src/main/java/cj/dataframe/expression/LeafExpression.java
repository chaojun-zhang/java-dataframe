package cj.dataframe.expression;

import io.vavr.collection.List;
import io.vavr.collection.Seq;

public interface LeafExpression extends Expression {

    @Override
    default Seq<Expression> children() {
        return List.empty();
    }
}
