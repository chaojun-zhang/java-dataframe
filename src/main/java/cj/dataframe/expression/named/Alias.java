package cj.dataframe.expression.named;

import cj.dataframe.expression.Expression;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

import java.util.Objects;

public final class Alias implements NamedExpression {
  private final Expression child;
  private final String name;
  private final ExpressionId expressionId;


  public Alias(Expression child, String name, ExpressionId expressionId) {
    this.child = child;
    this.name = name;
    this.expressionId = expressionId;
  }

  public Alias(Expression child, String name) {
    this(child, name, ExpressionId.newExpressionId());
  }

  @Override
  public Seq<Expression> children() {
    return List.of(child);
  }

  @Override
  public Seq<Object> productElements() {
    return List.of(child, name, expressionId);
  }

  @Override
  public Object evaluate(Row input) {
    return child.evaluate(input);
  }

  @Override
  public DataType dataType() {
    return child.dataType();
  }


  @Override
  public Attribute attribute() {
    if (child.isResolved()) {
      return new ResolvedAttribute(name, child.dataType(), child.isNullable(), expressionId);
    } else {
      return new UnresolvedAttribute(name);
    }
  }

  public Expression child() {
    return child;
  }

  public String name() {
    return name;
  }

  public ExpressionId expressionId() {
    return expressionId;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Alias) obj;
    return Objects.equals(this.child, that.child) &&
            Objects.equals(this.name, that.name) &&
            Objects.equals(this.expressionId, that.expressionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(child, name, expressionId);
  }

  @Override
  public String toString() {
    return "Alias[" +
            "child=" + child + ", " +
            "name=" + name + ", " +
            "expressionId=" + expressionId + ']';
  }

}
