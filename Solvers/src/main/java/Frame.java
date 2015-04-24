/**
 * <p>
 * </p>
 * <p>Created at 2015-04-22 10-15.</p>
 */
public final class Frame {
    public static final byte FRAME_SIZE = 3;
    public static final String INVALID_FRAME_SIZE_MESSAGE = "Invalid frame size. It must be 3 x 3";

    private final Element[][] elements;


    public Frame(Element[][] elements) {
        if (elements.length != FRAME_SIZE) throw new AssertionError(INVALID_FRAME_SIZE_MESSAGE);
        for (Element[] elements1 : elements) {
            if (elements1.length != FRAME_SIZE) throw new AssertionError(INVALID_FRAME_SIZE_MESSAGE);
        }

        this.elements = elements;

    }

    public final Element[][] getElements(){
        return this.elements;
    }

    /**
     * @param row {@code int} row of the frame.
     * @return Array of {@link Element} of the row.
     */
    public final Element[] getElementsOfRow(int row){
        if (row < 0 || row >= FRAME_SIZE)
            throw new AssertionError("Row must be greater or equal to zero and less than " + FRAME_SIZE);
        return this.getElements()[row].clone();
    }

    /**
     * @param column {@code int} column of the frame.
     * @return Array of {@link Element} of the column.
     */
    public final Element[] getElementsOfColumn(int column){
        if (column < 0 || column >= FRAME_SIZE)
            throw new AssertionError("Column must be greater or equal to zero and less than " + FRAME_SIZE);
        Element[] elements = new Element[FRAME_SIZE];
        for (int i = 0; i < FRAME_SIZE; i++) {
            elements[i] = this.elements[i][column];
        }
        return elements;
    }

    /**
     * @param element {@link Element} instance.
     * @param row     {@code byte} value of the row of the frame.
     * @return {@code true} if the {@code element} is present in the {@code row}, {@code false} otherwise.
     */
    public final boolean inRow(Element element, int row) {
        if (element == null) throw new AssertionError("Element must not be null.");
        if (row < 0 || row >= FRAME_SIZE)
            throw new AssertionError("Row must be greater or equal to zero and less than " + FRAME_SIZE);
        for (int i = 0; i < elements[row].length; i++) {
            if (elements[row][i].equals(element))
                return true;
        }
        return false;
    }

    /**
     * @param element {@link Element} instance.
     * @param column  {@code byte} value of the row of the frame.
     * @return {@code true} if the {@code element} is present in the {@code column}, {@code false} otherwise.
     */
    public final boolean inColumn(Element element, int column) {
        if (element == null) throw new AssertionError("Element must not be null.");
        if (column < 0 || column >= FRAME_SIZE)
            throw new AssertionError("Column must be greater or equal to zero and less than " + FRAME_SIZE);
        for (Element[] element1 : elements) {
            if (element1[column].equals(element))
                return true;
        }
        return false;
    }

    /**
     * Verify if all the elements in frame are different.
     *
     * @return {@code true} if the Frame is valid, {@code false} otherwise.
     */
    public final boolean isValid() {
        for (int i = 0; i < FRAME_SIZE * FRAME_SIZE; i++) {
            int originRow = i / FRAME_SIZE;
            int originColumn = i % FRAME_SIZE;
            Element origin = elements[originRow][originColumn];
            if (origin.getValue() == Element.EMPTY_VALUE) {
                return false;
            }
            for (int j = i + 1; j < FRAME_SIZE * FRAME_SIZE; j++) {
                int destRow = j / FRAME_SIZE;
                int destColumn = j % FRAME_SIZE;
                Element dest = elements[destRow][destColumn];
                if (origin.equals(dest) || (dest.getValue() == Element.EMPTY_VALUE)) {
                    return false;
                }
            }
        }
        return true;
    }


}
