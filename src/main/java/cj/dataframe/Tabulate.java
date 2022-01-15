package cj.dataframe;

import cj.dataframe.api.DataFrame;
import cj.dataframe.types.StructField;
import cj.dataframe.types.StructType;
import io.vavr.Tuple2;
import io.vavr.collection.Array;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 以tabulate格式显示
 */
public class Tabulate {

    private final DataFrame input;

    public Tabulate(DataFrame input) {
        this.input = input;
    }

    public void show(boolean truncate) {
        System.out.println(tabulate(truncate));
    }

    private String tabulate(boolean truncate) {
        StructType schema = input.getSchema();
        List<List<String>> fieldRow = new ArrayList<>();
        fieldRow.add( schema.getFields().map(StructField::toString).toJavaList());
        List<List<String>> dataRows = input.iterator()
                .take(20).map(it -> it.getValues()
                        .map(Object::toString)
                        .toJavaList()
                ).collect(Collectors.toList());
        List<List<String>> fieldsAndRows = StreamEx.of(fieldRow).append(dataRows).map(it -> it.stream().map(cell -> {
            String content = cell == null ? "NULL" : cell;
            if (truncate && content.length() > 20) {
                return content.substring(0, 17) + "...";
            } else {
                return content;
            }

        }).collect(Collectors.toList())).toList();
        return tabulate(fieldsAndRows, truncate);
    }

    private String tabulate(List<List<String>> rows, boolean truncate) {
        List<Integer> columnWidths = StreamEx.of(rows).foldLeft(Array.fill(rows.size(), 0), (maxWidthSoFar, nextRow) -> {
            Array<Tuple2<Integer, Integer>> zipped = maxWidthSoFar.zip(Array.ofAll(nextRow).map(String::length));
            return zipped.map((tuple) -> Math.max(tuple._1, tuple._2));
        }).map(it -> it + 2).toJavaList();

        String topBorder    = io.vavr.collection.List.ofAll(columnWidths).map(it -> StringUtils.repeat("=", it)).mkString("╒", "╤", "╕\n");
        String separator    = io.vavr.collection.List.ofAll(columnWidths).map(it -> StringUtils.repeat("-", it)).mkString("├", "┼", "┤\n");
        String bottomBorder = io.vavr.collection.List.ofAll(columnWidths).map(it -> StringUtils.repeat("=", it)).mkString("╘", "╧", "╛\n");


        Function<List<String>, String> displayRow = (row) -> io.vavr.collection.List.ofAll(row).zip(columnWidths).map(it -> {
            String content = it._1;
            int width = it._2;
            if (truncate) {
                return StringUtils.repeat(" ", (width - content.length() - 1)) + content + " ";
            } else {
                return " " + StringUtils.rightPad(content, width - 2) + " ";
            }
        }).mkString("│", "│", "│" + "\n");

        StringBuilder builder = new StringBuilder();
        io.vavr.collection.List<String> body = io.vavr.collection.List.ofAll(rows).map(displayRow);
        //头部
        builder.append(topBorder).append(body.head()).append(separator);
        //数据
        body.tail().forEach(builder::append);
        //尾部
        builder.append(bottomBorder);
        builder.append("Only showing top 20 row(s)");
        return builder.toString();
    }


}
