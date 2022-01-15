package cj.dataframe.exception;

import cj.dataframe.api.DataFrame;
import com.qiniu.defy.statd.search.Granularity;

public class TransformExceptions {

    public static StatdException throwProjectFieldsNotFound(String message) {
        throw new StatdException(TransformError.FieldNotFoundInSourceDataFrame, message);
    }

    public static StatdException throwFilterFieldsNotFound(String message) {
        throw new StatdException(TransformError.FieldNotFoundInSourceDataFrame, message);
    }

    public static StatdException throwDimensionFieldNotFoundInDataFrame(String field) {
        return new StatdException(TransformError.FieldNotFoundInSourceDataFrame, String.format("dimension '%s' not found in source dataFrame", field));
    }

    public static StatdException throwMetricFieldNotFoundInDataFrame(String field) {
        return new StatdException(TransformError.FieldNotFoundInSourceDataFrame, String.format("metric '%s' not found in source dataFrame", field));
    }

    public static void throwInvalidAggregatorIfPossible(DataFrame dataFrame, Granularity granularity) {
        if (!Granularity.isNone(granularity)  ) {
            throw new StatdException(TransformError.InvalidAggregatorError, "aggregation with granularity require a event time based dataframe");
        }
    }
}
