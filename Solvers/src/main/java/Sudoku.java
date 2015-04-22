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

    public boolean isRowValid(byte row){
        if (row < 0 || row >= SUDOKU_ELEMENT_SIZE) throw new AssertionError("Row must be greater or equal to zero and less than "+SUDOKU_ELEMENT_SIZE);
        return true;
    }

    private Frame[] framesOfRow(byte row){
        return frames[row/SUDOKU_FRAME_SIZE];
    }


}
