package cj.dataframe.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public enum StorageRequestError implements ErrorCodeSupplier {
    //无效时间格式
    InvalidTimeFormat(50100),
    //无效的时间粒度
    InvalidGranule(50101),
    //topn limit参数错误
    InvalidTopN(50102),
    //无效的时间范围
    InvalidInterval(50103),
    //无效的查询字段
    InvalidQueryField(50104),

    //无需的查询值
    InvalidQueryValue(50105),

    //维度字段找不到
    DimensionFieldNotFound(50106),

    InvalidDimension(50107),

    //指标字段找不到
    MetricFieldNotFound(50108),
    InvalidAggregation(50109),
    InvalidFilter(50110);

    private final ErrorCode errorCode;

    StorageRequestError(int code) {
        this.errorCode = new ErrorCode(code,
                String.format("%s,%s", BAD_REQUEST.getReasonPhrase(), name()),
                ErrorType.USER_ERROR);
    }

    @Override
    public ErrorCode toErrorCode() {
        return errorCode;
    }
}
