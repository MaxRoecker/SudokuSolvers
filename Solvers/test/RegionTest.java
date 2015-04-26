import model.Element;
import model.Frame;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-22 13-06.</p>
 */
public class RegionTest {

    Element[][] validFrameElements = {
            {new Element((byte) 1), new Element((byte) 2), new Element((byte) 3)},
            {new Element((byte) 4), new Element((byte) 5), new Element((byte) 6)},
            {new Element((byte) 7), new Element((byte) 8), new Element((byte) 9)}
    };

    Element[][] invalidFrameElements = {
            {new Element((byte) 0), new Element((byte) 2), new Element((byte) 3)},
            {new Element((byte) 4), new Element((byte) 5), new Element((byte) 6)},
            {new Element((byte) 7), new Element((byte) 8), new Element((byte) 9)}
    };


    @Test
    public void testInRow() throws Exception {
        int rowZero = 0;
        Element inRowZero = new Element((byte) 3);
        Element outRowZero = new Element((byte) 4);

        Frame frame = new Frame(validFrameElements);

        assertTrue(frame.inRow(inRowZero, rowZero));
        assertFalse(frame.inRow(outRowZero, rowZero));
    }

    @Test
    public void testInColumn() throws Exception {
        int rowOne = 1;
        Element outColumnOne = new Element((byte) 0);
        Element inColumnOne = new Element((byte) 8);

        Frame frame = new Frame(validFrameElements);

        assertFalse(frame.inColumn(outColumnOne, rowOne));
        assertTrue(frame.inColumn(inColumnOne, rowOne));
    }

    @Test
    public void testIsValid() throws Exception {
        Frame validFrame = new Frame(validFrameElements);
        Frame invalidFrame = new Frame(invalidFrameElements);

        assertTrue(validFrame.isValid());
        assertFalse(invalidFrame.isValid());
    }

    @Test
    public void testGetElementsOfRow() throws Exception {
        int row = 0;
        Element[] elementsValid = {new Element((byte) 1), new Element((byte) 2), new Element((byte) 3)};
        Element[] elementsInvalid = {new Element((byte) 1), new Element((byte) 4), new Element((byte) 7)};
        Frame frame = new Frame(validFrameElements);

        assertTrue(Arrays.equals(frame.getElementsOfRow(row), elementsValid));
        assertFalse(Arrays.equals(frame.getElementsOfRow(row),elementsInvalid));
    }

    @Test
    public void testGetElementsOfColumn() throws Exception {
        int column = 1;
        Element[] elementsValid = {new Element((byte) 2), new Element((byte) 5), new Element((byte) 8)};
        Element[] elementsInvalid = {new Element((byte) 3), new Element((byte) 6), new Element((byte) 9)};
        Frame frame = new Frame(validFrameElements);

        assertTrue(Arrays.equals(frame.getElementsOfColumn(column), elementsValid));
        assertFalse(Arrays.equals(frame.getElementsOfColumn(column),elementsInvalid));
    }
}