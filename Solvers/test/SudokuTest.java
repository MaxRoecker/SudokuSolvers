import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by joao on 4/22/15.
 */
public class SudokuTest {

    Element[][] frame00 = {
            {new Element((byte) 2), new Element((byte) 5), new Element((byte) 7)},
            {new Element((byte) 6), new Element((byte) 4), new Element((byte) 3)},
            {new Element((byte) 9), new Element((byte) 8), new Element((byte) 1)}
    };

    Element[][] frame01 = {
            {new Element((byte) 1), new Element((byte) 4), new Element((byte) 6)},
            {new Element((byte) 9), new Element((byte) 2), new Element((byte) 8)},
            {new Element((byte) 3), new Element((byte) 5), new Element((byte) 7)}
    };

    Element[][] frame02 = {
            {new Element((byte) 9), new Element((byte) 3), new Element((byte) 8)},
            {new Element((byte) 1), new Element((byte) 5), new Element((byte) 7)},
            {new Element((byte) 6), new Element((byte) 4), new Element((byte) 2)}
    };

    Element[][] frame10 = {
            {new Element((byte) 5), new Element((byte) 7), new Element((byte) 9)},
            {new Element((byte) 8), new Element((byte) 3), new Element((byte) 6)},
            {new Element((byte) 1), new Element((byte) 2), new Element((byte) 4)}
    };

    Element[][] frame11 = {
            {new Element((byte) 6), new Element((byte) 3), new Element((byte) 4)},
            {new Element((byte) 7), new Element((byte) 1), new Element((byte) 2)},
            {new Element((byte) 8), new Element((byte) 9), new Element((byte) 5)}
    };

    Element[][] frame12 = {
            {new Element((byte) 8), new Element((byte) 2), new Element((byte) 1)},
            {new Element((byte) 5), new Element((byte) 9), new Element((byte) 4)},
            {new Element((byte) 7), new Element((byte) 6), new Element((byte) 3)}
    };

    Element[][] frame20 = {
            {new Element((byte) 7), new Element((byte) 9), new Element((byte) 8)},
            {new Element((byte) 4), new Element((byte) 1), new Element((byte) 2)},
            {new Element((byte) 3), new Element((byte) 6), new Element((byte) 5)}
    };

    Element[][] frame21 = {
            {new Element((byte) 2), new Element((byte) 6), new Element((byte) 3)},
            {new Element((byte) 5), new Element((byte) 8), new Element((byte) 9)},
            {new Element((byte) 4), new Element((byte) 7), new Element((byte) 1)}
    };

    Element[][] frame22 = {
            {new Element((byte) 4), new Element((byte) 1), new Element((byte) 5)},
            {new Element((byte) 3), new Element((byte) 7), new Element((byte) 6)},
            {new Element((byte) 2), new Element((byte) 8), new Element((byte) 9)}
    };

    Frame[][] validFrames = {
            {new Frame(frame00),new Frame(frame01),new Frame(frame02)},
            {new Frame(frame10),new Frame(frame11),new Frame(frame12)},
            {new Frame(frame20),new Frame(frame21),new Frame(frame22)},
    };

    Frame[][] invalidFrames = {
            {new Frame(frame00),new Frame(frame00),new Frame(frame00)},
            {new Frame(frame00),new Frame(frame00),new Frame(frame00)},
            {new Frame(frame00),new Frame(frame00),new Frame(frame00)}
    };

    @Test
    public void testIsRowValid() throws Exception {
        Sudoku sudokuValid = new Sudoku(validFrames);
        Sudoku sudokuInvalid = new Sudoku(invalidFrames);

        assertTrue(sudokuValid.isRowValid(0));
        assertFalse(sudokuInvalid.isRowValid(0));
    }

    @Test
    public void testIsColumnValid() throws Exception {
        Sudoku sudokuValid = new Sudoku(validFrames);
        Sudoku sudokuInvalid = new Sudoku(invalidFrames);

        assertTrue(sudokuValid.isColumnValid(0));
        assertFalse(sudokuInvalid.isColumnValid(0));
    }

    @Test
    public void testFramesByRow() throws Exception {
        Sudoku sudokuValid = new Sudoku(validFrames);
        Frame[] framesOfRowZero = {new Frame(frame00),new Frame(frame01),new Frame(frame02)};

        assertTrue(Arrays.equals(framesOfRowZero,sudokuValid.framesByRow(0)));
        assertFalse(Arrays.equals(framesOfRowZero, sudokuValid.framesByRow(3)));
    }

    @Test
    public void testFramesByColumn() throws Exception {
        Sudoku sudokuValid = new Sudoku(validFrames);
        Frame[] framesOfColumnZero = {new Frame(frame01),new Frame(frame11),new Frame(frame21)};

        assertTrue(Arrays.equals(framesOfColumnZero,sudokuValid.framesByColumn(4)));
        assertFalse(Arrays.equals(framesOfColumnZero, sudokuValid.framesByColumn(1)));
    }

    @Test
    public void testElementsOfRow() throws Exception {
        Sudoku sudokuValid = new Sudoku(validFrames);
        Element[] elements = {
                new Element((byte) 2),
                new Element((byte) 5),
                new Element((byte) 7),
                new Element((byte) 1),
                new Element((byte) 4),
                new Element((byte) 6),
                new Element((byte) 9),
                new Element((byte) 3),
                new Element((byte) 8)};

        assertTrue(Arrays.equals(elements,sudokuValid.elementsOfRow(0)));
        assertFalse(Arrays.equals(elements, sudokuValid.elementsOfRow(8)));
    }

    @Test
    public void testElementsOfColumn() throws Exception {
        Sudoku sudokuValid = new Sudoku(validFrames);
        Element[] elements = {
                new Element((byte) 5),
                new Element((byte) 4),
                new Element((byte) 8),

                new Element((byte) 7),
                new Element((byte) 3),
                new Element((byte) 2),

                new Element((byte) 9),
                new Element((byte) 1),
                new Element((byte) 6)};

        assertTrue(Arrays.equals(elements, sudokuValid.elementsOfColumn(1)));
        assertFalse(Arrays.equals(elements,sudokuValid.elementsOfColumn(8)));
    }

    @Test
    public void testIsValid() throws Exception {
        Sudoku sudokuValid = new Sudoku(validFrames);
        Sudoku sudokuInvalid = new Sudoku(invalidFrames);

        assertTrue(sudokuValid.isValid());
        assertFalse(sudokuInvalid.isValid());
    }
}