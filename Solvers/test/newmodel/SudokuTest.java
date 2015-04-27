package newmodel;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-24 21-18.</p>
 */
public class SudokuTest {

    int[] validValues = {
            1, 2, 3,   4, 5, 6,   7, 8, 9,
            7, 8, 9,   1, 2, 3,   4, 5, 6,
            4, 5, 6,   7, 8, 9,   1, 2, 3,

            9, 1, 2,   3, 4, 5,   6, 7, 8,
            6, 7, 8,   9, 1, 2,   3, 4, 5,
            3, 4, 5,   6, 7, 8,   9, 1, 2,

            8, 9, 1,   2, 3, 4,   5, 6, 7,
            5, 6, 7,   8, 9, 1,   2, 3, 4,
            2, 3, 4,   5, 6, 7,   8, 9, 1
    };

    @Test
    public void testGetOrder() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        assertEquals(sudoku.getOrder(), 3);
    }

    @Test
    public void testIsCellRowValid() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        for (int i = 0; i < 9; i++) {
            assertTrue(sudoku.isCellRowValid(i));
        }
    }

    @Test
    public void testIsCellColumnValid() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        for (int i = 0; i < 9; i++) {
            assertTrue(sudoku.isCellColumnValid(i));
        }
    }

    @Test
    public void testIsValid() throws Exception {
        Sudoku sudoku = new Sudoku(3, validValues);
        assertTrue(sudoku.isValid());
    }


}