import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by joao on 4/22/15.
 */
public class SudokuTest {

    Element[][] validFrameElements = {
            {new Element((byte) 1), new Element((byte) 2), new Element((byte) 3)},
            {new Element((byte) 4), new Element((byte) 5), new Element((byte) 6)},
            {new Element((byte) 7), new Element((byte) 8), new Element((byte) 9)}
    };

    @Test
    public void testPrint() throws Exception {
        Frame[][] frames = new Frame[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                frames[i][j] = new Frame(validFrameElements.clone());
            }
        }

        Sudoku sudoku = new Sudoku(frames);
        sudoku.print();
    }

}