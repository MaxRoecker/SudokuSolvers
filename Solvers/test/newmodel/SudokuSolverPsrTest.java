package newmodel;

import org.junit.Test;
import util.Util;

import static org.junit.Assert.*;

/**
 * Created by joao on 7/6/15.
 */
public class SudokuSolverPsrTest {

    @Test
    public void testSolve() throws Exception {
        Sudoku sudoku = new Sudoku(3, Util.readFromFile("easy.txt"));
        SudokuSolverPsr sudokuSolverPsr = new SudokuSolverPsr(sudoku);
        Sudoku solved = sudokuSolverPsr.solve();
        System.out.println(solved.prettyPrint());
    }


}