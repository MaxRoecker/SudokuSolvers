package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * <p>
 *     Represents a element of a frame inside a model.Sudoku board.
 * </p>
 * <p>Created at 2015-04-22 10-13.</p>
 */
public class Element{
    public static final byte EMPTY_VALUE = 0;
    public static final byte MIN_VALUE = 1;
    public static final byte MAX_VALUE = 9;
    private static final String INVALID_ELEMENT_VALUE_MESSAGE = "Invalid value to element.";

    private final byte value;
    private final boolean fixed;

    public Element(byte value) {
        this(value,false);
    }

    public Element(byte value, boolean fixed) {
        if(value != EMPTY_VALUE)
            if (!((value >= MIN_VALUE) && (value <= MAX_VALUE))) throw new AssertionError(INVALID_ELEMENT_VALUE_MESSAGE);
        this.value = value;
        this.fixed = fixed;
    }

    public final byte getValue() {
        return value;
    }

    public final boolean isEmpty(){
        return this.getValue() == EMPTY_VALUE;
    }

    public final boolean isFixed() {
        return fixed;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        return new EqualsBuilder()
                .append(getValue(), element.getValue())
                .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getValue())
                .toHashCode();
    }

    @Override
    public final String toString(){
        return String.valueOf(this.value);
    }
}
