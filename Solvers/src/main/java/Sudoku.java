import org.apache.commons.lang3.ArrayUtils;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-22 09-54.</p>
 */
public final class Sudoku {
    public static final byte SUDOKU_FRAME_SIZE = 3;
    public static final byte SUDOKU_ELEMENT_SIZE = 9;
    private static final String INVALID_SUDOKU_INSTANCE = "Invalid Sudoku instance";

    private final Frame[][] frames;
    private final int[] numberOfElements  = new int[SUDOKU_ELEMENT_SIZE + 1];;

    public Sudoku(Frame[][] frames) {
        if (frames.length != SUDOKU_FRAME_SIZE) throw new AssertionError(INVALID_SUDOKU_INSTANCE);
        for (Frame[] frames1 : frames) {
            if (frames1.length != SUDOKU_FRAME_SIZE) throw new AssertionError(INVALID_SUDOKU_INSTANCE);
        }
        this.frames = frames;

        for (int i = 0; i < this.numberOfElements.length; i++) {
            this.numberOfElements[i] = 0;
        }

        for (Frame[] frames1 : this.frames) {
            for (Frame frame : frames1) {
                for (Element[] elements : frame.getElements()){
                    for (Element element : elements){
                        this.numberOfElements[((int) element.getValue())]++;
                    }
                }
            }
        }
    }

    /**
     * TODO Terminar isso aqui.
     * @param elementColumn
     * @return
     */
    public boolean isColumnValid(byte elementColumn){
        return true;
    }

    /**
     * @param elementRow {@code int} of the row.
     * @return {@code true} if the row of {@link Element} is valid, {@code false} otherwise.
     */
    public boolean isRowValid(byte elementRow) {
        if (elementRow < 0 || elementRow >= SUDOKU_ELEMENT_SIZE)
            throw new AssertionError("Row must be greater or equal to zero and less than " + SUDOKU_ELEMENT_SIZE);
        Element[] elements = elementsOfRow(elementRow);
        for (int i = 0; i < elements.length; i++) {
            Element element = elements[i];
            for (int j = i; j < elements.length; j++) {
                if (element.equals(elements[j]))
                    return false;
            }
        }
        return true;
    }

    /**
     * @param elementRow Index of row.
     * @return Array of {@link Frame} in the row.
     */
    public Frame[] framesByRow(int elementRow) {
        if (elementRow < 0 || elementRow >= SUDOKU_ELEMENT_SIZE)
            throw new AssertionError("Row must be greater or equal to zero and less than " + SUDOKU_ELEMENT_SIZE);
        return frames[elementRow / SUDOKU_FRAME_SIZE];
    }

    /**
     * TODO terminar isso aqui
     * @param elementColumn Index of column.
     * @return Array of {@link Frame} in the column.
     */
    public Frame[] framesByColumn(int elementColumn){
        if (elementColumn < 0 || elementColumn >= SUDOKU_ELEMENT_SIZE)
            throw new AssertionError("Column must be greater or equal to zero and less than " + SUDOKU_ELEMENT_SIZE);
        Frame[] frames = new Frame[SUDOKU_FRAME_SIZE];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = this.frames[i][elementColumn / SUDOKU_FRAME_SIZE];
        }
        return frames;
    }

    /**
     * @param elementRow Index of the row.
     * @return Array of {@link Element} in the row.
     */
    public Element[] elementsOfRow(int elementRow) {
        if (elementRow < 0 || elementRow >= SUDOKU_ELEMENT_SIZE)
            throw new AssertionError("Row must be greater or equal to zero and less than " + SUDOKU_ELEMENT_SIZE);
        Frame[] frames = framesByRow(elementRow);
        Element[] rowElements = {};
        for (Frame frame : frames) {
            rowElements = ArrayUtils.addAll(rowElements, frame.getElementsOfRow(elementRow % SUDOKU_FRAME_SIZE));
        }
        return rowElements;
    }

    /**
     * TODO Terminar isso aqui
     * @param elementColumn Index of the column.
     * @return Array of {@link Element} in the column.
     */
    public Element[] elementsOfColumn(int elementColumn) {
        if (elementColumn < 0 || elementColumn >= SUDOKU_ELEMENT_SIZE)
            throw new AssertionError("Column must be greater or equal to zero and less than " + SUDOKU_ELEMENT_SIZE);
        Frame[] frames = framesByRow(elementColumn);
        Element[] rowElements = {};
        for (Frame frame : frames) {
            rowElements = ArrayUtils.addAll(rowElements, frame.getElementsOfRow(elementColumn % SUDOKU_FRAME_SIZE));
        }
        return rowElements;
    }

    public final void print() {
        for (int i = 0; i < SUDOKU_FRAME_SIZE; i++) {
            Frame frame1 = this.frames[i][0];
            Frame frame2 = this.frames[i][1];
            Frame frame3 = this.frames[i][2];
            for (int j = 0; j < Frame.FRAME_SIZE; j++) {
                Element[] elementsFrame1 = frame1.getElementsOfRow(j);
                Element[] elementsFrame2 = frame2.getElementsOfRow(j);
                Element[] elementsFrame3 = frame3.getElementsOfRow(j);
                for (int k = 0; k < Frame.FRAME_SIZE; k++) {
                    System.out.print(elementsFrame1[k] + " ");
                }
                System.out.print(" ");
                for (int k = 0; k < Frame.FRAME_SIZE; k++) {
                    System.out.print(elementsFrame2[k] + " ");
                }
                System.out.print(" ");
                for (int k = 0; k < Frame.FRAME_SIZE; k++) {
                    System.out.print(elementsFrame3[k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }


}
