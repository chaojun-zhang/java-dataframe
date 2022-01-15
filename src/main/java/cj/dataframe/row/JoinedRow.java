package cj.dataframe.row;

import io.vavr.collection.List;
import io.vavr.collection.Seq;

public record JoinedRow(Row left, Row right) implements Row {

    @Override
    public <T> T getAs(int ordinal) {
        if (ordinal < left.size()) {
            return left.getAs(ordinal);
        } else {
            return right.getAs(ordinal - left.size());
        }
    }

    @Override
    public Object get(int ordinal) {
        if (ordinal < left.size()) {
            return left.get(ordinal);
        } else {
            return right.get(ordinal - left.size());
        }
    }

    @Override
    public int size() {
        return left.size() + right.size();
    }

    @Override
    public boolean isEmpty() {
        return left.isEmpty() && right.isEmpty();
    }

    @Override
    public Seq<Object> getValues() {
        return List.ofAll(left.getValues().iterator().concat(right.getValues().iterator()));
    }
}
