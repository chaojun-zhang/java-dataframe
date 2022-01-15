package cj.dataframe.expression;

import cj.dataframe.expression.named.Attribute;
import cj.dataframe.row.Row;
import cj.dataframe.tree.TreeNode;
import cj.dataframe.types.DataType;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;

public interface Expression extends TreeNode<Expression> {

    Object evaluate(Row input);

    DataType dataType();

    default Object evaluated() {
        return evaluate(null);
    }

    default Set<Attribute> referenceSet() {
        return references().toSet();
    }

    default boolean isNullable() {
        return children().exists(Expression::isNullable);
    }

    default boolean isBound() {
        return children().forAll(Expression::isBound);
    }

    default boolean isResolved() {
        return children().forAll(Expression::isResolved);
    }

    default Seq<Attribute> references() {
        return children().flatMap(Expression::references).distinct();
    }



}
