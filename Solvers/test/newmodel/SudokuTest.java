package newmodel;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-24 21-18.</p>
 */
public class SudokuTest {

    int[] validValues = {
            1, 2, 3, 4, 5, 6, 7, 8, 1,
            7, 8, 9, 1, 2, 3, 4, 5, 6,
            4, 5, 6, 7, 8, 9, 1, 2, 3,

            9, 1, 2, 3, 4, 5, 6, 7, 8,
            6, 7, 8, 9, 1, 2, 3, 4, 5,
            3, 4, 5, 6, 7, 8, 9, 1, 2,

            8, 9, 1, 2, 3, 4, 5, 6, 7,
            5, 6, 7, 8, 9, 1, 2, 3, 4,
            2, 3, 4, 5, 6, 7, 8, 9, 1
    };

    @Test
    public void testGetOrder() throws Exception {
        Sudoku sudoku = new Sudoku(3, validValues);
        assertEquals(sudoku.getOrder(), 3);
    }

    @Test
    public void testIsCellRowValid() throws Exception {
        Sudoku sudoku = new Sudoku(3, validValues);
        for (int i = 0; i < 9; i++) {
            assertTrue(sudoku.isCellRowValid(i));
        }
    }

    @Test
    public void testIsCellColumnValid() throws Exception {
        Sudoku sudoku = new Sudoku(3, validValues);
        for (int i = 0; i < 9; i++) {
            assertTrue(sudoku.isCellColumnValid(i));
        }
    }

    @Test
    public void testCellsRelationByCell() throws Exception{
        Sudoku sudoku = new Sudoku(3, validValues);
        Sudoku.Cell cell = new Sudoku.Cell(6,6,5);
        Sudoku.Cell[] cells = sudoku.cellsRegionByCell(cell);
        int index = 0;
        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                assertEquals(sudoku.getCells()[i][j],cells[index++]);
            }
        }
    }

    @Test
    public void testIsValid() throws Exception {
        Sudoku sudoku = new Sudoku(3, validValues);
        assertTrue(sudoku.isValid());
    }

    @Test
    public void testGetRowRelations() throws Exception {
        Sudoku sudoku = new Sudoku(3, validValues);
        Sudoku.Cell cell = new Sudoku.Cell(0,0,1);
        Sudoku.Cell[] rowRelations = sudoku.getRowRelations(cell);
        for (int i = 1; i < 9; i++) {
            assertEquals(validValues[i],rowRelations[i-1].getValue());
        }
    }

    @Test
    public void testGetColumnRelation() throws Exception{
        Sudoku sudoku = new Sudoku(3, validValues);
        Sudoku.Cell cell = new Sudoku.Cell(0,0,1);
        Sudoku.Cell[] columnRelations = sudoku.getColumnRelations(cell);
        for (int i = 0; i < 8; i++) {
            assertEquals(validValues[(i+1)*9],columnRelations[i].getValue());
        }
    }

    @Test
    public void testGetRegionRelations() throws Exception{
        Sudoku sudoku = new Sudoku(3, validValues);
        Sudoku.Cell cell = new Sudoku.Cell(0,0,1);
        Sudoku.Cell[] regionRelations = sudoku.getRegionRelations(cell);
        assertEquals(validValues[1],regionRelations[0].getValue());
        assertEquals(validValues[2],regionRelations[1].getValue());
        assertEquals(validValues[9],regionRelations[2].getValue());
        assertEquals(validValues[10],regionRelations[3].getValue());
        assertEquals(validValues[11],regionRelations[4].getValue());
        assertEquals(validValues[18],regionRelations[5].getValue());
        assertEquals(validValues[19],regionRelations[6].getValue());
        assertEquals(validValues[20], regionRelations[7].getValue());
    }
}