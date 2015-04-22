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

    public Sudoku(Frame[][] frames) {
        if (frames.length != SUDOKU_FRAME_SIZE) throw new AssertionError(INVALID_SUDOKU_INSTANCE);
        for (Frame[] frames1 : frames) {
            if (frames1.length != SUDOKU_FRAME_SIZE) throw new AssertionError(INVALID_SUDOKU_INSTANCE);
        }
        this.frames = frames;
    }

    public boolean isRowValid(byte row) {
        if (row < 0 || row >= SUDOKU_ELEMENT_SIZE)
            throw new AssertionError("Row must be greater or equal to zero and less than " + SUDOKU_ELEMENT_SIZE);
        return true;
    }

    private Frame[] framesOfRow(byte row) {
        return frames[row / SUDOKU_FRAME_SIZE];
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
                System.out.print("  ");
                for (int k = 0; k < Frame.FRAME_SIZE; k++) {
                    System.out.print(elementsFrame3[k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }


}
