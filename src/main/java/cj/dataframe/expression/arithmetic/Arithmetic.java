package cj.dataframe.expression.arithmetic;

import cj.dataframe.expression.Expression;
import cj.dataframe.types.Numeric;
import cj.dataframe.types.NumericType;

public interface Arithmetic extends Expression {


    default Numeric numeric() {
        NumericType numericType = dataType().as();
        return numericType.numeric();
    }
}
