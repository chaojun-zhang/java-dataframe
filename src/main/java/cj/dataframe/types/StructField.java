package cj.dataframe.types;

import cj.dataframe.exception.StatdException;
import cj.dataframe.expression.Expression;
import cj.dataframe.expression.named.ResolvedAttribute;

import java.util.Objects;

import static cj.dataframe.exception.StorageConfigError.SchemaConfigError;


public final class StructField {
    private final String name;
    private final DataType dataType;
    private final boolean nullable;

    public StructField(String name,
                       DataType dataType,
                       boolean nullable) {
        this.name = name;
        this.dataType = dataType;
        this.nullable = nullable;
    }

    public StructField(String name, DataType dataType) {
        this(name, dataType, true);
    }

    public Expression toAttribute() {
        return new ResolvedAttribute(name, dataType, nullable);
    }

    public void validate() throws StatdException {
        if (name == null) {
            throw new StatdException(SchemaConfigError, "name is null");
        }
        if (dataType == null) {
            throw new StatdException(SchemaConfigError, "dataType is null");
        }
    }

    public String toString() {
        return this.name;
    }

    public String name() {
        return name;
    }

    public DataType dataType() {
        return dataType;
    }

    public boolean nullable() {
        return nullable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (StructField) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.dataType, that.dataType) &&
                this.nullable == that.nullable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dataType, nullable);
    }


}
    
