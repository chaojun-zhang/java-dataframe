package cj.dataframe.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public final class ErrorCode
{
    private final int code;
    private final String name;
    private final ErrorType type;
    private final boolean retriable;

    @JsonCreator
    public ErrorCode(
            @JsonProperty("code") int code,
            @JsonProperty("name") String name,
            @JsonProperty("type") ErrorType type,
            @JsonProperty("retriable") boolean retriable)
    {
        if (code < 0) {
            throw new IllegalArgumentException("code is negative");
        }
        this.code = code;
        this.name = requireNonNull(name, "name is null");
        this.type = requireNonNull(type, "type is null");
        this.retriable = retriable;
    }

    public ErrorCode(int code, String name, ErrorType type)
    {
        this(code, name, type, false);
    }

    @JsonProperty
    public int getCode()
    {
        return code;
    }

    @JsonProperty
    public String getName()
    {
        return name;
    }

    @JsonProperty
    public ErrorType getType()
    {
        return type;
    }

    @JsonProperty
    public boolean isRetriable()
    {
        return retriable;
    }

    @Override
    public String toString()
    {
        return name + ":" + code;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ErrorCode that = (ErrorCode) obj;
        return Objects.equals(this.code, that.code);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(code);
    }
}