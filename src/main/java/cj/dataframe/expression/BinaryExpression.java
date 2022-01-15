package cj.dataframe.expression;

import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

public interface BinaryExpression extends Expression {
    
  Expression left();

  Expression right();


  @Override
  default Seq<Expression> children() {
    return List.of(left(), right());
  }

  @Override
  default DataType dataType() {
    return left().dataType();
  }

  Object nullSafeEvaluate(Object leftValue, Object rightValue);

  @Override
  default Object evaluate(Row input) {
    var leftValue = left().evaluate(input);
    var rightValue = right().evaluate(input);
    if (leftValue != null && rightValue != null) {
      return nullSafeEvaluate(leftValue, rightValue);
    } else if (leftValue == null) {
      return rightValue;
    } else {
      return leftValue;
    }
  }
}
