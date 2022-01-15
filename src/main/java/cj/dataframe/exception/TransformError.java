package cj.dataframe.exception;

import static cj.dataframe.exception.ErrorType.INTERNAL_ERROR;

public enum TransformError implements ErrorCodeSupplier {

    FieldNotFoundInSourceDataFrame(50200),
    InvalidAggregatorError(50201);
    private final ErrorCode errorCode;

    TransformError(int code) {
        this.errorCode = new ErrorCode(code, name(), INTERNAL_ERROR);
    }

    @Override
    public ErrorCode toErrorCode() {
        return errorCode;
    }
}
