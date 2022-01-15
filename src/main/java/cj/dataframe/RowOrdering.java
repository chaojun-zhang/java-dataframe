package cj.dataframe;

import cj.dataframe.expression.Expression;
import cj.dataframe.expression.logical.comparision.SortOrder;
import cj.dataframe.expression.named.Attribute;
import cj.dataframe.row.Row;
import cj.dataframe.types.DataType;
import com.google.common.collect.Ordering;
import io.vavr.collection.Seq;

import javax.annotation.Nullable;
import java.util.Comparator;

public class RowOrdering implements Comparator<Row> {
    static final int LEFT_IS_GREATER = 1;
    static final int RIGHT_IS_GREATER = -1;

    private final Seq<SortOrder> bindSortOrders;

    private final Seq<Ordering> nullSafeOrderings;

    public RowOrdering(Seq<Attribute> inputSchema,
                       Seq<SortOrder> sortOrders) {
        this.bindSortOrders = sortOrders;
        nullSafeOrderings = this.bindSortOrders.map(it -> {
            if (it.direction().isAsc()) {
                return new NullSafeOrdering(it.dataType(), it.nullsLarger());
            } else {
                return new NullSafeOrdering(it.dataType(), it.nullsLarger()).reverse();
            }
        });
    }


    @Override
    public int compare(Row a, Row b) {
        Seq<Expression> children = bindSortOrders.map(SortOrder::child);
        return children.zip(nullSafeOrderings).iterator().map(it -> {
            Expression expression = it._1;
            Ordering ordering = it._2;
            return ordering.compare(expression.evaluate(a), expression.evaluate(b));
        }).find(it -> it != 0).getOrElse(0);
    }

    public final class NullSafeOrdering extends Ordering {

        private final boolean nullsLarger;
        private final Ordering baseOrdering;

        public NullSafeOrdering(DataType dataType, boolean nullsLarger) {
            this.nullsLarger = nullsLarger;
            this.baseOrdering = dataType.ordering();
        }

        @Override
        public int compare(@Nullable Object left, @Nullable Object right) {
            if (left == null && right == null) {
                return 0;
            } else if (left == null) {
                return nullsLarger ? LEFT_IS_GREATER : RIGHT_IS_GREATER;
            } else if (right == null) {
                return nullsLarger ? RIGHT_IS_GREATER : LEFT_IS_GREATER;
            } else {
                return baseOrdering.compare(left, right);
            }
        }
    }
}
