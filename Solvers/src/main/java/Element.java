import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * <p>
 *     Represents a element of a frame inside a Sudoku board.
 * </p>
 * <p>Created at 2015-04-22 10-13.</p>
 */
public final class Element implements Comparable<Element>{
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

    public byte getValue() {
        return value;
    }

    public boolean isEmpty(){
        return this.getValue() == EMPTY_VALUE;
    }

    public boolean isFixed() {
        return fixed;
    }

    @Override
    public int compareTo(Element o) {
        return this.getValue() - o.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        return new EqualsBuilder()
                .append(getValue(), element.getValue())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getValue())
                .toHashCode();
    }
}
