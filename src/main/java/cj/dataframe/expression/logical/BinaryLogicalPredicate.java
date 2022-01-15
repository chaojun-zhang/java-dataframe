package cj.dataframe.expression.logical;

import cj.dataframe.expression.BinaryExpression;
import cj.dataframe.types.DataType;
import cj.dataframe.types.DataTypes;

public interface BinaryLogicalPredicate extends BinaryExpression {

    @Override
    default DataType dataType(){
        return DataTypes.BOOLEAN;
    }

}

