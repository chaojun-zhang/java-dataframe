package cj.dataframe.row;


import com.google.common.base.Joiner;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

import java.util.Arrays;
import java.util.Objects;


/**
 * row implementation with java array
 */
public record GenericRow(Object[] values) implements Row {

    public GenericRow(Object[] values) {
        this.values = Objects.requireNonNull(values);
    }

    @Override
    public <T> T getAs(int i) {
        return (T) get(i);
    }


    @Override
    public int size() {
        return values.length;
    }

    public Object get(int i) {
        return values[i];
    }

    @Override
    public boolean isEmpty() {
        return this.values.length == 0;
    }

    @Override
    public Seq<Object> getValues() {
        return List.of(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenericRow)) {
            return false;
        }
        GenericRow that = (GenericRow) o;
        return Arrays.deepEquals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(values);
    }

    public String toString() {
        return Joiner.on(",").join(values);
    }

}
