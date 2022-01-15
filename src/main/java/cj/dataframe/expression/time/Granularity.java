package cj.dataframe.expression.time;

import cj.dataframe.expression.Expression;
import cj.dataframe.expression.UnaryExpression;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public record Granularity(Expression child, com.qiniu.defy.statd.search.Granularity granularity) implements UnaryExpression {

    @Override
    public Seq<Object> productElements() {
        return List.of(child, granularity);
    }

    @Override
    public Object nullSafeEvaluate(Object value) {
        switch (value) {
            case Long it-> {
                Timestamp time = Timestamp.from(Instant.ofEpochMilli(it));
                return Timestamp.valueOf(granularity.getDateTime(time.toLocalDateTime())).getTime();
            }
            case Timestamp it-> {
                return Timestamp.valueOf(granularity.getDateTime(it.toLocalDateTime()));
            }
            case Date it->{
                Timestamp time = Timestamp.from(Instant.ofEpochMilli(it.getTime()));
                return new Date(Timestamp.valueOf(granularity.getDateTime(time.toLocalDateTime())).getTime());
            }
            default -> {
                throw new IllegalStateException("unexpected value:" + value);
            }
        }
    }
}
