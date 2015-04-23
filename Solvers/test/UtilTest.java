import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by joao on 4/22/15.
 */
public class UtilTest {

    @Test
    public void testReadFromFile(){
        Frame[][] frames = Util.readFromFile("easy.txt");
        Sudoku sudoku = new Sudoku(frames);
        sudoku.print();

    }
}