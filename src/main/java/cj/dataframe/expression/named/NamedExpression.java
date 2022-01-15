package cj.dataframe.expression.named;

import cj.dataframe.expression.Expression;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Expression that is named
 */
public interface NamedExpression extends Expression {

    AtomicLong currentID = new AtomicLong(0L);

    ExpressionId expressionId();

    String name();

    Attribute attribute();
}
