package cj.dataframe.expression.logical.comparision;

import cj.dataframe.expression.UnaryExpression;
import cj.dataframe.types.DataType;
import cj.dataframe.types.DataTypes;
import com.google.common.collect.Ordering;

public interface UnaryComparison extends UnaryExpression {
    @Override
    default DataType dataType() {
        return DataTypes.BOOLEAN;
    }

    default Ordering<?> ordering() {
        return child().dataType().ordering();
    }
}
