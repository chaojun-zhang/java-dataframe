package cj.dataframe.api;

import cj.dataframe.expression.Expression;
import cj.dataframe.expression.aggregator.AggregatedExpression;
import cj.dataframe.expression.aggregator.Average;
import cj.dataframe.expression.aggregator.First;
import cj.dataframe.expression.aggregator.Last;
import cj.dataframe.expression.aggregator.Max;
import cj.dataframe.expression.aggregator.Min;
import cj.dataframe.expression.aggregator.Sum;
import cj.dataframe.expression.aggregator.TopN;
import cj.dataframe.expression.arithmetic.Divide;
import cj.dataframe.expression.arithmetic.Minus;
import cj.dataframe.expression.arithmetic.Multiply;
import cj.dataframe.expression.arithmetic.Plus;
import cj.dataframe.expression.arithmetic.Remainder;
import cj.dataframe.expression.functions;
import cj.dataframe.expression.logical.And;
import cj.dataframe.expression.logical.IsNotNull;
import cj.dataframe.expression.logical.Not;
import cj.dataframe.expression.logical.Or;
import cj.dataframe.expression.logical.comparision.Eq;
import cj.dataframe.expression.logical.comparision.Gt;
import cj.dataframe.expression.logical.comparision.Gte;
import cj.dataframe.expression.logical.comparision.In;
import cj.dataframe.expression.logical.comparision.Lt;
import cj.dataframe.expression.logical.comparision.Lte;
import cj.dataframe.expression.logical.comparision.NotEq;
import cj.dataframe.expression.logical.comparision.SortDirection;
import cj.dataframe.expression.logical.comparision.SortOrder;
import cj.dataframe.expression.named.Alias;
import cj.dataframe.expression.named.NamedExpression;
import cj.dataframe.expression.named.Star;
import cj.dataframe.expression.named.UnresolvedAlias;
import cj.dataframe.expression.named.UnresolvedAttribute;
import cj.dataframe.expression.types.Cast;
import cj.dataframe.types.DataType;
import io.vavr.control.Option;
import lombok.Getter;

import java.util.List;

public class Column {

    @Getter
    private final Expression expr;

    public Column(Expression expr) {
        this.expr = expr;
    }

    public Column(String colName) {
        this(new UnresolvedAttribute(colName));
    }

    private Column withExpr(Expression expression) {
        return new Column(expression);
    }

    public Column gt(Object that) {
        return withExpr(new Gt(expr, functions.typedLit(that).expr));
    }

    public Column lt(Object that) {
        return withExpr(new Lt(expr, functions.typedLit(that).expr));
    }

    public Column gte(Object that) {
        return withExpr(new Gte(expr, functions.typedLit(that).expr));
    }

    public Column lte(Object that) {
        return withExpr(new Lte(expr, functions.typedLit(that).expr));
    }


    public Column ne(Object that) {
        return withExpr(new NotEq(expr, functions.typedLit(that).expr));
    }

    public Column eq(Object that) {
        return withExpr(new Eq(expr, functions.typedLit(that).expr));
    }

    public Column plus(Object that) {
        return withExpr(new Plus(expr, functions.typedLit(that).expr));
    }

    public Column minus(Object that) {
        return withExpr(new Minus(expr, functions.typedLit(that).expr));
    }

    public Column multiple(Object that) {
        return withExpr(new Multiply(expr, functions.typedLit(that).expr));
    }

    public Column divide(Object that) {
        return withExpr(new Divide(expr, functions.typedLit(that).expr));
    }

    public Column and(Object that) {
        return withExpr(new And(expr, functions.typedLit(that).expr));
    }

    public Column or(Object that) {
        return withExpr(new Or(expr, functions.typedLit(that).expr));
    }


    public Column asc(boolean nullLarger) {
        return withExpr(new SortOrder(expr, SortDirection.ASC, nullLarger));
    }

    public Column desc(boolean nullLarger) {
        return withExpr(new SortOrder(expr, SortDirection.DESC, nullLarger));
    }


    public Column in(List<Object> collection) {
        return withExpr(new In(expr, io.vavr.collection.List.ofAll(collection).map(it -> functions.lit(it).expr)));
    }

    public Column notIn(List<Object> collection) {
        return withExpr(new Not(new In(expr, io.vavr.collection.List.ofAll(collection).map(it -> functions.lit(it).expr))));
    }

    public Column isNotNull() {
        return withExpr(new IsNotNull(expr));
    }

    public Column as(String name) {
        return withExpr(new Alias(expr, name));
    }

    public Column remainder(Object that) {
        return withExpr(new Remainder(expr, functions.lit(that).expr));
    }

    public Column max() {
        return withExpr(new Max(expr));
    }

    public Column min() {
        return withExpr(new Min(expr));
    }

    public Column sum() {
        return withExpr(new Sum(expr));
    }

    public Column peak(int maximumSize) {
        return withExpr(new TopN(expr, maximumSize));
    }

    public Column first() {
        return withExpr(new First(expr));
    }

    public Column last() {
        return withExpr(new Last(expr));
    }

    public Column avg() {
        return withExpr(new Average(expr));
    }


    public Column between(Object lowerBound, Object upperBound) {
        return gte(lowerBound).and(lte(upperBound));
    }

    public Column cast(DataType dataType) {
        if (expr.dataType().isCastableTo(dataType)) {
            return withExpr(new Cast(expr, dataType));
        } else {
            throw new IllegalArgumentException("unexpected value: " + dataType);
        }
    }

    public NamedExpression named() {
        if (expr instanceof NamedExpression) {
            return (NamedExpression) expr;
        } else {
            return new UnresolvedAlias(expr);
        }
    }

    public Option<AggregatedExpression> aggregated() {
        if (expr instanceof AggregatedExpression) {
            return Option.some((AggregatedExpression) expr);
        } else {
            return Option.none();
        }
    }

    public static Column all(){
        return new Column(new Star());
    }

}
