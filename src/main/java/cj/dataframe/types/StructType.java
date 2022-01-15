package cj.dataframe.types;

import cj.dataframe.expression.named.Attribute;
import cj.dataframe.expression.named.ResolvedAttribute;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


public final class StructType {
    @Getter
    private final Seq<StructField> fields;
    private final Map<String, Integer> nameToIndex = new HashMap<>();
    private final Map<String, StructField> nameToField = new HashMap<>();
    private final Map<Integer, StructField> indexToField = new HashMap<>();

    public StructType(Seq<StructField> fields){
        this.fields = fields;
        int idx = 0;
        for (StructField field : fields) {
            nameToIndex.put(field.name(), idx);
            nameToField.put(field.name(), field);
            indexToField.put(idx, field);
            idx++;
        }
    }

    public static StructType fromAttribute(Seq<Attribute> attributes) {
        return new StructType(attributes.map(it -> new StructField(it.name(), it.dataType(), it.isNullable())));
    }

    public Option<StructField> get(String name) {
        return Option.of(nameToField.get(name));
    }

    public Option<StructField> get(int index) {
        return Option.of(indexToField.get(index));
    }

    public Option<Integer> getIndex(String name) {
        return Option.of(nameToIndex.get(name));
    }

    public int length() {
        return fields.size();
    }

    public Seq<Attribute> toAttributes() {
        return fields.map(it -> new ResolvedAttribute(it.name(), it.dataType(), it.nullable()));
    }

    public void validate() {
        fields.forEach(StructField::validate);
    }

    public static StructType fromString(String value) {
        if (value == null) {
            throw new NullPointerException("schema is null");
        }
        String[] fieldParts = value.split(",\\s*");
        List<StructField> structFields = List.of(fieldParts).map(StructType::parseField);
        return new StructType(structFields);
    }

    private static StructField parseField(String fieldPart) {
        if (fieldPart == null) {
            throw new NullPointerException("field is null");
        }
        String[] nameAndType = fieldPart.trim().split("\\s+");

        if (nameAndType.length != 2) {
            throw new IllegalArgumentException("field must contain name and type");
        }

        DataType dataType = DataTypes.fromTypeString(nameAndType[1]);

        return new StructField(nameAndType[0], dataType);
    }

}
