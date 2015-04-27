package util;

import newmodel.Sudoku;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by joao on 4/27/15.
 */
public class UtilTest {

    @Test
    public void testReadFromFile() throws Exception {
        int[] elements = Util.readFromFile("easy.txt");
        int order = (int) Math.sqrt(Math.sqrt(elements.length));
        Sudoku sudoku = new Sudoku(order,elements);
        assertEquals(order,sudoku.getOrder());
    }
}