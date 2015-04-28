package newmodel;

import org.junit.Test;
import util.Util;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-28 16-14.</p>
 */
public class SudokuSolverAStarTest {

    @Test
    public void testSolve() throws Exception {
        Sudoku sudoku = new Sudoku(3, Util.readFromFile("test.txt"));
        SudokuSolverAStar solverAStar = new SudokuSolverAStar(sudoku);
        Sudoku solved = solverAStar.solve();
        if(solved != null){
            System.out.println(solved.prettyPrint());
        }else{
            System.out.println("Not possible to solve.");
        }
    }
}