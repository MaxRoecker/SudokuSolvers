package newmodel;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-24 18-15.</p>
 */
public class CellTest {

    Sudoku.Region region = new Sudoku.Region(3,0,0);

    @Test
    public void testGetRegion() throws Exception {
        Sudoku.Cell cellVolatile = new Sudoku.Cell(region,1,1,0);
        assertEquals(cellVolatile.getRegion(),region);
    }

    @Test
    public void testGetCellRow() throws Exception {
        Sudoku.Cell cellVolatile = new Sudoku.Cell(region,1,1,0);
        assertEquals(cellVolatile.getCellRow(),1);
    }

    @Test
    public void testGetCellColumn() throws Exception {
        Sudoku.Cell cellVolatile = new Sudoku.Cell(region,1,1,0);
        assertEquals(cellVolatile.getCellColumn(), 1);
    }

    @Test
    public void testGetValue() throws Exception {
        Sudoku.Cell cellVolatile = new Sudoku.Cell(region,1,1,0);
        assertEquals(cellVolatile.getValue(),0);
    }

    @Test
    public void testSetValue() throws Exception {
        Sudoku.Cell cellVolatile = new Sudoku.Cell(region,1,1,0);
        Sudoku.Cell cellFixed = new Sudoku.Cell(region,1,1,1);
        cellVolatile.setValue(1);
        cellFixed.setValue(0);
        assertEquals(cellFixed.getValue(), 0);
        assertEquals(cellVolatile.getValue(),1);
    }

    @Test
    public void testIsFixed() throws Exception {
        Sudoku.Cell cellVolatile = new Sudoku.Cell(region,1,1,0);
        Sudoku.Cell cellFixed = new Sudoku.Cell(region,1,1,1);
        assertFalse(cellVolatile.isFixed());
        assertTrue(cellFixed.isFixed());
    }

    @Test
    public void testIsEmpty() throws Exception {
        Sudoku.Cell cellVolatile = new Sudoku.Cell(region,1,1,0);
        Sudoku.Cell cellFixed = new Sudoku.Cell(region,1,1,1);
        assertTrue(cellVolatile.isEmpty());
        assertFalse(cellFixed.isEmpty());
    }

    @Test
    public void testEquals() throws Exception {
        Sudoku.Cell cell1 = new Sudoku.Cell(region,1,1,0);
        Sudoku.Cell cell2 = new Sudoku.Cell(region,1,1,0);
        assertTrue(cell1.equals(cell2));
    }
}