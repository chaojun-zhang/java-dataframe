package cj.dataframe.expression.logical;

import cj.dataframe.expression.UnaryExpression;
import cj.dataframe.types.DataType;
import cj.dataframe.types.DataTypes;

public interface UnaryLogicalPredicate extends UnaryExpression {

    @Override
    default DataType dataType(){
        return DataTypes.BOOLEAN;
    }

}

