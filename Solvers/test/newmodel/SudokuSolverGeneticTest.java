package newmodel;

import org.junit.Test;
import util.Util;

import static org.junit.Assert.*;

/**
 * Created by joao on 4/28/15.
 */
public class SudokuSolverGeneticTest {

    @Test
    public void testConstrutor(){
        Sudoku sudoku = new Sudoku(3, Util.readFromFile("easy.txt"));
        //System.out.println(sudoku.prettyPrint());

        SudokuSolverGenetic solver = new SudokuSolverGenetic(sudoku,10);

    }

    @Test
    public void testCalculePopulationRanking() throws Exception {
        Sudoku sudoku = new Sudoku(3, Util.readFromFile("easy.txt"));
    }


    @Test
    public void testSolve() throws Exception {
        Sudoku sudoku = new Sudoku(3, Util.readFromFile("easy.txt"));
        SudokuSolverGenetic.Conditions conditions = new SudokuSolverGenetic.Conditions(5000,1000,900);

        SudokuSolverGenetic genetic = new SudokuSolverGenetic(sudoku,20);
        Sudoku solve = genetic.solve(conditions);
        System.out.println();
        System.out.println(solve.prettyPrint());
    }

}