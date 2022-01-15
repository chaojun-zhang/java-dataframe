package cj.dataframe.row;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface TypedGetter {

    default byte[] getBinary(int ordinal){
        return getAs(ordinal);
    }

    <T> T getAs(int ordinal);

    Object get(int ordinal);

    default boolean isNullAt(int ordinal){
        return getAs(ordinal) ==null;
    }

    default boolean getBoolean(int ordinal){
        return getAs(ordinal);
    }

    default byte getByte(int ordinal){
        return getAs(ordinal);
    }

    default short getShort(int ordinal){
        return getAs(ordinal);
    }

    default int getInt(int ordinal){
        return getAs(ordinal);
    }

    default long getLong(int ordinal){
        return getAs(ordinal);
    }

    default float getFloat(int ordinal){
        return getAs(ordinal);
    }

    default double getDouble(int ordinal){
        return getAs(ordinal);
    }

    default BigDecimal getDecimal(int ordinal){
        return getAs(ordinal);
    }

    default String getString(int ordinal){
        return getAs(ordinal);
    }

    default Timestamp getTimestamp(int ordinal){
        return getAs(ordinal);
    }
}
