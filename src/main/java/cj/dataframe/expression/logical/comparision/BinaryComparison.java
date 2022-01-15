package cj.dataframe.expression.logical.comparision;

import cj.dataframe.expression.BinaryExpression;
import cj.dataframe.types.DataType;
import cj.dataframe.types.DataTypes;
import com.google.common.collect.Ordering;

public interface BinaryComparison extends BinaryExpression {
    @Override
    default DataType dataType() {
        return DataTypes.BOOLEAN;
    }


    default Ordering ordering() {
        return left().dataType().ordering();
    }

}
