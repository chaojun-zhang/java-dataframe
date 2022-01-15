package cj.dataframe.types;

public interface Numeric<T> {

    T plus(T left, T right);

    T minus(T left, T right);

    T times(T left, T right);

    T divide(T left, T right);

    T max(T left, T right);

    T min(T left, T right);

    T abs(T value);

    T negate(T value);

    T rem(T left, T right);

}
