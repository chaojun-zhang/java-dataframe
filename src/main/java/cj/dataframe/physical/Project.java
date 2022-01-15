package cj.dataframe.physical;

import cj.dataframe.api.DataFrame;
import cj.dataframe.expression.named.Attribute;
import cj.dataframe.expression.named.NamedExpression;
import cj.dataframe.row.Row;
import cj.dataframe.row.RowFactory;
import io.vavr.collection.Iterator;
import io.vavr.collection.Seq;

public record Project(Seq<NamedExpression> projectList,
                      DataFrame input) implements DataFrame {

    @Override
    public Iterator<Row> iterator() {
        return input.iterator().map(row -> {
            Object[] values = projectList.map(it -> it.evaluate(row)).toJavaArray();
            return RowFactory.create(values);
        });
    }

    @Override
    public Seq<Attribute> output() {
        return projectList.map(NamedExpression::attribute);
    }

}
