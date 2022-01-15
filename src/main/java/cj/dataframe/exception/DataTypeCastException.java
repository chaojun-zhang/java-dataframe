package cj.dataframe.exception;

import cj.dataframe.types.DataType;

public class DataTypeCastException extends RuntimeException {


    public DataTypeCastException(DataType from, DataType to) {
        super(String.format("Can not cast type from %s to %s", from, to));
    }
}
