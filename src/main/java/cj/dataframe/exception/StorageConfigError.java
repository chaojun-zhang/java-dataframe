package cj.dataframe.exception;

public enum StorageConfigError implements ErrorCodeSupplier {

    StorageNotFound(50001),
    StorageDatasourceNotDefine(50002),
    StorageNotDefine(50003),
    JdbcDatasourceConfigError(50004),
    MongoDatasourceConfigError(50005),
    MongoStorageConfigError(50006),
    JdbcStorageConfigError(50007),
    TimelineStorageConfigError(50008),
    GranuleStorageConfigError(50009),
    SchemaConfigError(50010),
    JdbcDatasourceNotFound(50011),
    MongoDatasourceNotFound(50012),
    PeakStorageConfigError(50013),
    StorageEventTimeNotFound(50014);

    private final ErrorCode errorCode;

    StorageConfigError(int code) {
        this.errorCode = new ErrorCode(code, name(), ErrorType.INTERNAL_ERROR);
    }

    @Override
    public ErrorCode toErrorCode() {
        return errorCode;
    }
}
