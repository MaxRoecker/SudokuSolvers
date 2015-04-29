package newmodel;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by joao on 4/28/15.
 */
public class PersonTest {

    @Test
    public void testObjective() throws Exception {
        Sudoku.Cell[] cells = new Sudoku.Cell[9];
        Random r = new Random();
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Sudoku.Cell(0,i,i+1);
        }
        cells[2] = new Sudoku.Cell(0,2,8);
        cells[5] = new Sudoku.Cell(0,5,7);
        cells[8] = new Sudoku.Cell(0,8,1);

        SudokuSolverGenetic.Person person = new SudokuSolverGenetic.Person(cells);

        assertEquals(3,person.objective());
    }
}