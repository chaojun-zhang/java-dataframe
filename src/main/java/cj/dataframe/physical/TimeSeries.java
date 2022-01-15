package cj.dataframe.physical;

import cj.dataframe.api.DataFrame;
import cj.dataframe.event.TimeEvent;
import cj.dataframe.row.Row;
import cj.dataframe.row.RowFactory;
import cj.dataframe.types.NumericType;
import com.qiniu.defy.statd.search.Granularity;
import io.vavr.collection.Iterator;
import io.vavr.collection.Seq;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TimeSeries {
    private final DataFrame input;
    private final Granularity granularity;
    private final String eventTimeField;
    private final Seq<String> dimensionFields;
    private final Seq<String> metricFields;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public TimeSeries(DataFrame input,
                      Granularity granularity,
                      String eventTimeField,
                      Seq<String> dimensionFields,
                      Seq<String> metricFields,
                      LocalDateTime from,
                      LocalDateTime to) {
        this.input = input;
        this.granularity = granularity;
        this.eventTimeField = eventTimeField;
        this.dimensionFields = dimensionFields;
        this.metricFields = metricFields;
        this.from = from;
        this.to = to;
    }

    /**
     * 构建时序数据，先找出所有缺失维度和缺失的时间进行填充
     *
     * @return timestamp->dimension->metric
     */
    private Map<LocalDateTime, Map<Row, Row>> fillZero() {
        final Map<LocalDateTime, Map<Row, Row>> eventToDimensionMetric = new HashMap<>();
        int eventTimeIdx = input.getSchema().getIndex(eventTimeField).getOrElseThrow(() -> new IllegalStateException("no event time field found in source"));

        Set<Row> dimensionRows = new HashSet<>();
        input.forEach(row -> {
            LocalDateTime timestamp = row.getTimestamp(eventTimeIdx).toLocalDateTime();
            Map<Row, Row> dimensionValues = eventToDimensionMetric.computeIfAbsent(timestamp, k -> new HashMap<>());
            Row dimension = createDimensionRow(row);
            Row metric = createMetricRow(row);
            dimensionValues.put(dimension, metric);
            dimensionRows.add(dimension);

        });

        LocalDateTime start = from;
        LocalDateTime end = to;
        while (start.isBefore(end)) {
            //fill missing time
            Map<Row, Row> dimensionMetric = eventToDimensionMetric.computeIfAbsent(from, k -> createDimensionMetric(dimensionRows));
            for (Row dimension : dimensionRows) {
                //fill missing dimension
                dimensionMetric.computeIfAbsent(dimension, k -> createEmptyMetric());
            }
            start = granularity.nextTime(start);
        }
        return eventToDimensionMetric;
    }

    private Row createMetricRow(Row row) {
        Object[] result = new Object[metricFields.size()];
        int i = 0;
        for (String metric : metricFields) {
            int metricIdx = input.getSchema().getIndex(metric).getOrElseThrow(() -> new IllegalStateException(String.format(" no field %s found in source")));
            result[i++] = row.get(metricIdx);
        }
        return RowFactory.create(result);
    }

    private Row createDimensionRow(Row row) {
        Object[] result = new Object[dimensionFields.size()];
        int i = 0;
        for (String dimension : dimensionFields) {
            int dimensionIdx = input.getSchema().getIndex(dimension).getOrElseThrow(() -> new IllegalStateException(String.format(" no field %s found in source")));
            result[i++] = row.get(dimensionIdx);
        }
        return RowFactory.create(result);
    }

    private Map<Row, Row> createDimensionMetric(Set<Row> dimensions) {
        Map<Row, Row> dimensionMetrics = new HashMap<>();
        dimensions.forEach(dimension -> {
            Row metric = createEmptyMetric();
            dimensionMetrics.put(dimension, metric);
        });
        return dimensionMetrics;
    }

    private Row createEmptyMetric() {
        Object[] result = new Object[metricFields.size()];
        for (int i = 0; i < metricFields.size(); i++) {
            NumericType numericType = input.getSchema().get(metricFields.get(i)).get().dataType().as();
            result[i++] = numericType.initValue();
        }
        return RowFactory.create(result);
    }

    public List<TimeEvent> toEvents() {
        Map<LocalDateTime, Map<Row, Row>> timestampToMetric = this.fillZero();
        List<TimeEvent> timeEvents = new ArrayList<>();
        timestampToMetric.forEach((timestamp, dimensionMetric) -> {
            TimeEvent timeEvent = new TimeEvent();
            timeEvent.setTimestamp(timestamp);
            timeEvent.setEvents(new ArrayList<>(dimensionMetric.size()));
            dimensionMetric.forEach((dimension, metric) -> {
                Map<String, Object> fieldNameToValue = new HashMap<>(dimension.size() + metric.size());
                for (int i = 0; i < dimensionFields.size(); i++) {
                    fieldNameToValue.put(dimensionFields.get(i), dimension.get(i));
                }
                for (int i = 0; i < metricFields.size(); i++) {
                    fieldNameToValue.put(metricFields.get(i), metric.get(i));
                }
                timeEvent.getEvents().add(fieldNameToValue);
            });
        });
        Collections.sort(timeEvents);
        return timeEvents;
    }

    public Iterator<Row> iterator() {
        Map<LocalDateTime, Map<Row, Row>> eventTimeToMetric = fillZero();
        List<Row> metricRows = new ArrayList<>();
        eventTimeToMetric.forEach((time, dimensionToMetric) -> {
            dimensionToMetric.forEach((dimension, metric) -> {
                Object[] row = new Object[1 + dimension.size() + metric.size()];
                int start = 0;
                row[start++] = Timestamp.valueOf(time);
                for (Object d : dimension.getValues()) {
                    row[start++] = d;
                }
                for (Object m : metric.getValues()) {
                    row[start++] = m;
                }
                metricRows.add(RowFactory.create(row));
            });
        });
        return Iterator.ofAll(metricRows);
    }


}
