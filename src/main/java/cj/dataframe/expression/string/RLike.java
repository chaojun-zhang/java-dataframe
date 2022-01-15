package cj.dataframe.expression.string;

import cj.dataframe.expression.Expression;
import cj.dataframe.expression.logical.BinaryLogicalPredicate;
import io.vavr.Lazy;

import java.util.regex.Pattern;

public class RLike implements BinaryLogicalPredicate {

    private final Expression left;
    private final Expression right;
    private final Lazy<Pattern> pattern;

    public RLike(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        this.pattern = Lazy.of(()->Pattern.compile(right.evaluated().toString()));
    }


    @Override
    public Expression left() {
        return left;
    }

    @Override
    public Expression right() {
        return right;
    }

    @Override
    public Object nullSafeEvaluate(Object leftValue, Object rightValue) {
        return pattern.get().matcher(leftValue.toString()).matches();
    }
}
