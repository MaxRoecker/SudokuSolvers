package newmodel;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-24 21-18.</p>
 */
public class SudokuTest {

    int[] validValues = {
            1, 2, 3,   4, 5, 6,   7, 8, 9,
            7, 8, 9,   1, 2, 3,   4, 5, 6,
            4, 5, 6,   7, 8, 9,   1, 2, 3,

            9, 1, 2,   3, 4, 5,   6, 7, 8,
            6, 7, 8,   9, 1, 2,   3, 4, 5,
            3, 4, 5,   6, 7, 8,   9, 1, 2,

            8, 9, 1,   2, 3, 4,   5, 6, 7,
            5, 6, 7,   8, 9, 1,   2, 3, 4,
            2, 3, 4,   5, 6, 7,   8, 9, 1
    };

    Sudoku.Region region00 = new Sudoku.Region(3,0,0);
    Sudoku.Region region01 = new Sudoku.Region(3,0,1);
    Sudoku.Region region02 = new Sudoku.Region(3,0,2);
    Sudoku.Region region10 = new Sudoku.Region(3,1,0);
    Sudoku.Region region11 = new Sudoku.Region(3,1,1);
    Sudoku.Region region12 = new Sudoku.Region(3,1,2);
    Sudoku.Region region20 = new Sudoku.Region(3,2,0);
    Sudoku.Region region21 = new Sudoku.Region(3,2,1);
    Sudoku.Region region22 = new Sudoku.Region(3,2,2);

    Sudoku.Region[][] regions = {
            {region00,region01,region02},
            {region10,region11,region12},
            {region20,region21,region22}
    };

    Sudoku.Cell cell00 = new Sudoku.Cell(region00,0,0,1);
    Sudoku.Cell cell01 = new Sudoku.Cell(region00,0,1,2);
    Sudoku.Cell cell02 = new Sudoku.Cell(region00,0,2,3);
    Sudoku.Cell cell03 = new Sudoku.Cell(region01,0,3,4);
    Sudoku.Cell cell04 = new Sudoku.Cell(region01,0,4,5);
    Sudoku.Cell cell05 = new Sudoku.Cell(region01,0,5,6);
    Sudoku.Cell cell06 = new Sudoku.Cell(region02,0,6,7);
    Sudoku.Cell cell07 = new Sudoku.Cell(region02,0,7,8);
    Sudoku.Cell cell08 = new Sudoku.Cell(region02,0,8,9);

    Sudoku.Cell cell10 = new Sudoku.Cell(region00,1,0,7);
    Sudoku.Cell cell11 = new Sudoku.Cell(region00,1,1,8);
    Sudoku.Cell cell12 = new Sudoku.Cell(region00,1,2,9);
    Sudoku.Cell cell13 = new Sudoku.Cell(region01,1,3,1);
    Sudoku.Cell cell14 = new Sudoku.Cell(region01,1,4,2);
    Sudoku.Cell cell15 = new Sudoku.Cell(region01,1,5,3);
    Sudoku.Cell cell16 = new Sudoku.Cell(region02,1,6,4);
    Sudoku.Cell cell17 = new Sudoku.Cell(region02,1,7,5);
    Sudoku.Cell cell18 = new Sudoku.Cell(region02,1,8,6);

    Sudoku.Cell cell20 = new Sudoku.Cell(region00,2,0,4);
    Sudoku.Cell cell21 = new Sudoku.Cell(region00,2,1,5);
    Sudoku.Cell cell22 = new Sudoku.Cell(region00,2,2,6);
    Sudoku.Cell cell23 = new Sudoku.Cell(region01,2,3,7);
    Sudoku.Cell cell24 = new Sudoku.Cell(region01,2,4,8);
    Sudoku.Cell cell25 = new Sudoku.Cell(region01,2,5,9);
    Sudoku.Cell cell26 = new Sudoku.Cell(region02,2,6,1);
    Sudoku.Cell cell27 = new Sudoku.Cell(region02,2,7,2);
    Sudoku.Cell cell28 = new Sudoku.Cell(region02,2,8,3);

    Sudoku.Cell cell30 = new Sudoku.Cell(region10,3,0,9);
    Sudoku.Cell cell31 = new Sudoku.Cell(region10,3,1,1);
    Sudoku.Cell cell32 = new Sudoku.Cell(region10,3,2,2);
    Sudoku.Cell cell33 = new Sudoku.Cell(region11,3,3,3);
    Sudoku.Cell cell34 = new Sudoku.Cell(region11,3,4,4);
    Sudoku.Cell cell35 = new Sudoku.Cell(region11,3,5,5);
    Sudoku.Cell cell36 = new Sudoku.Cell(region12,3,6,6);
    Sudoku.Cell cell37 = new Sudoku.Cell(region12,3,7,7);
    Sudoku.Cell cell38 = new Sudoku.Cell(region12,3,8,8);

    Sudoku.Cell cell40 = new Sudoku.Cell(region10,4,0,6);
    Sudoku.Cell cell41 = new Sudoku.Cell(region10,4,1,7);
    Sudoku.Cell cell42 = new Sudoku.Cell(region10,4,2,8);
    Sudoku.Cell cell43 = new Sudoku.Cell(region11,4,3,9);
    Sudoku.Cell cell44 = new Sudoku.Cell(region11,4,4,1);
    Sudoku.Cell cell45 = new Sudoku.Cell(region11,4,5,2);
    Sudoku.Cell cell46 = new Sudoku.Cell(region12,4,6,3);
    Sudoku.Cell cell47 = new Sudoku.Cell(region12,4,7,4);
    Sudoku.Cell cell48 = new Sudoku.Cell(region12,4,8,5);

    Sudoku.Cell cell50 = new Sudoku.Cell(region10,5,0,3);
    Sudoku.Cell cell51 = new Sudoku.Cell(region10,5,1,4);
    Sudoku.Cell cell52 = new Sudoku.Cell(region10,5,2,5);
    Sudoku.Cell cell53 = new Sudoku.Cell(region11,5,3,6);
    Sudoku.Cell cell54 = new Sudoku.Cell(region11,5,4,7);
    Sudoku.Cell cell55 = new Sudoku.Cell(region11,5,5,8);
    Sudoku.Cell cell56 = new Sudoku.Cell(region12,5,6,9);
    Sudoku.Cell cell57 = new Sudoku.Cell(region12,5,7,1);
    Sudoku.Cell cell58 = new Sudoku.Cell(region12,5,8,2);

    Sudoku.Cell cell60 = new Sudoku.Cell(region20,6,0,8);
    Sudoku.Cell cell61 = new Sudoku.Cell(region20,6,1,9);
    Sudoku.Cell cell62 = new Sudoku.Cell(region20,6,2,1);
    Sudoku.Cell cell63 = new Sudoku.Cell(region21,6,3,2);
    Sudoku.Cell cell64 = new Sudoku.Cell(region21,6,4,3);
    Sudoku.Cell cell65 = new Sudoku.Cell(region21,6,5,4);
    Sudoku.Cell cell66 = new Sudoku.Cell(region22,6,6,5);
    Sudoku.Cell cell67 = new Sudoku.Cell(region22,6,7,6);
    Sudoku.Cell cell68 = new Sudoku.Cell(region22,6,8,7);

    Sudoku.Cell cell70 = new Sudoku.Cell(region20,7,0,5);
    Sudoku.Cell cell71 = new Sudoku.Cell(region20,7,1,6);
    Sudoku.Cell cell72 = new Sudoku.Cell(region20,7,2,7);
    Sudoku.Cell cell73 = new Sudoku.Cell(region21,7,3,8);
    Sudoku.Cell cell74 = new Sudoku.Cell(region21,7,4,9);
    Sudoku.Cell cell75 = new Sudoku.Cell(region21,7,5,1);
    Sudoku.Cell cell76 = new Sudoku.Cell(region22,7,6,2);
    Sudoku.Cell cell77 = new Sudoku.Cell(region22,7,7,3);
    Sudoku.Cell cell78 = new Sudoku.Cell(region22,7,8,4);

    Sudoku.Cell cell80 = new Sudoku.Cell(region20,8,0,2);
    Sudoku.Cell cell81 = new Sudoku.Cell(region20,8,1,3);
    Sudoku.Cell cell82 = new Sudoku.Cell(region20,8,2,4);
    Sudoku.Cell cell83 = new Sudoku.Cell(region21,8,3,5);
    Sudoku.Cell cell84 = new Sudoku.Cell(region21,8,4,6);
    Sudoku.Cell cell85 = new Sudoku.Cell(region21,8,5,7);
    Sudoku.Cell cell86 = new Sudoku.Cell(region22,8,6,8);
    Sudoku.Cell cell87 = new Sudoku.Cell(region22,8,7,9);
    Sudoku.Cell cell88 = new Sudoku.Cell(region22,8,8,1);

    Sudoku.Cell[][] cells = {
            {cell00,cell01,cell02,cell03,cell04,cell05,cell06,cell07,cell08},
            {cell10,cell11,cell12,cell13,cell14,cell15,cell16,cell17,cell18},
            {cell20,cell21,cell22,cell23,cell24,cell25,cell26,cell27,cell28},
            {cell30,cell31,cell32,cell33,cell34,cell35,cell36,cell37,cell38},
            {cell40,cell41,cell42,cell43,cell44,cell45,cell46,cell47,cell48},
            {cell50,cell51,cell52,cell53,cell54,cell55,cell56,cell57,cell58},
            {cell60,cell61,cell62,cell63,cell64,cell65,cell66,cell67,cell68},
            {cell70,cell71,cell72,cell73,cell74,cell75,cell76,cell77,cell78},
            {cell80,cell81,cell82,cell83,cell84,cell85,cell86,cell87,cell88},
    };

    @Test
    public void testGetOrder() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        assertEquals(sudoku.getOrder(), 3);
    }

    @Test
    public void testGetCells() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        assertTrue(Arrays.deepEquals(this.cells,sudoku.getCells()));
    }

    @Test
    public void testGetRegions() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        assertTrue(Arrays.deepEquals(this.regions,sudoku.getRegions()));
    }

    @Test
    public void testCellsByRow() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        assertTrue(Arrays.deepEquals(this.cells[0],sudoku.cellsByRow(0)));
        assertTrue(Arrays.deepEquals(this.cells[4],sudoku.cellsByRow(4)));
        assertTrue(Arrays.deepEquals(this.cells[8],sudoku.cellsByRow(8)));
    }

    @Test
    public void testCellsByColumn() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        Sudoku.Cell[] cells0 = new Sudoku.Cell[9];
        Sudoku.Cell[] cells4 = new Sudoku.Cell[9];
        Sudoku.Cell[] cells8 = new Sudoku.Cell[9];
        for (int i = 0; i < 9; i++) {
            cells0[i] = cells[i][0];
            cells4[i] = cells[i][4];
            cells8[i] = cells[i][8];
        }
        assertTrue(Arrays.deepEquals(cells0,sudoku.cellsByColumn(0)));
        assertTrue(Arrays.deepEquals(cells4,sudoku.cellsByColumn(4)));
        assertTrue(Arrays.deepEquals(cells8,sudoku.cellsByColumn(8)));
    }

    @Test
    public void testRegionsByRow() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        assertTrue(Arrays.deepEquals(this.regions[0],sudoku.regionsByRow(0)));
        assertTrue(Arrays.deepEquals(this.regions[1],sudoku.regionsByRow(1)));
        assertTrue(Arrays.deepEquals(this.regions[2],sudoku.regionsByRow(2)));
    }

    @Test
    public void testRegionsByColumn() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        Sudoku.Region[] regions0 = new Sudoku.Region[3];
        Sudoku.Region[] regions1 = new Sudoku.Region[3];
        Sudoku.Region[] regions2 = new Sudoku.Region[3];
        for (int i = 0; i < 3; i++) {
            regions0[i] = regions[i][0];
            regions1[i] = regions[i][1];
            regions2[i] = regions[i][2];
        }
        assertTrue(Arrays.deepEquals(regions0,sudoku.regionsByColumn(0)));
        assertTrue(Arrays.deepEquals(regions1,sudoku.regionsByColumn(1)));
        assertTrue(Arrays.deepEquals(regions2,sudoku.regionsByColumn(2)));
    }

    @Test
    public void testRegionsByCellRow() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        assertTrue(Arrays.deepEquals(this.regions[0],sudoku.regionsByCellRow(0)));
        assertTrue(Arrays.deepEquals(this.regions[0],sudoku.regionsByCellRow(1)));
        assertTrue(Arrays.deepEquals(this.regions[0],sudoku.regionsByCellRow(2)));
        assertTrue(Arrays.deepEquals(this.regions[1],sudoku.regionsByCellRow(3)));
        assertTrue(Arrays.deepEquals(this.regions[1],sudoku.regionsByCellRow(4)));
        assertTrue(Arrays.deepEquals(this.regions[1],sudoku.regionsByCellRow(5)));
        assertTrue(Arrays.deepEquals(this.regions[2],sudoku.regionsByCellRow(6)));
        assertTrue(Arrays.deepEquals(this.regions[2],sudoku.regionsByCellRow(7)));
        assertTrue(Arrays.deepEquals(this.regions[2],sudoku.regionsByCellRow(8)));
    }

    @Test
    public void testRegionsByCellColumn() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        Sudoku.Region[] regions0 = new Sudoku.Region[3];
        Sudoku.Region[] regions1 = new Sudoku.Region[3];
        Sudoku.Region[] regions2 = new Sudoku.Region[3];
        for (int i = 0; i < 3; i++) {
            regions0[i] = regions[i][0];
            regions1[i] = regions[i][1];
            regions2[i] = regions[i][2];
        }
        assertTrue(Arrays.deepEquals(regions0,sudoku.regionsByCellColumn(0)));
        assertTrue(Arrays.deepEquals(regions0,sudoku.regionsByCellColumn(1)));
        assertTrue(Arrays.deepEquals(regions0,sudoku.regionsByCellColumn(2)));
        assertTrue(Arrays.deepEquals(regions1,sudoku.regionsByCellColumn(3)));
        assertTrue(Arrays.deepEquals(regions1,sudoku.regionsByCellColumn(4)));
        assertTrue(Arrays.deepEquals(regions1,sudoku.regionsByCellColumn(5)));
        assertTrue(Arrays.deepEquals(regions2,sudoku.regionsByCellColumn(6)));
        assertTrue(Arrays.deepEquals(regions2,sudoku.regionsByCellColumn(7)));
        assertTrue(Arrays.deepEquals(regions2,sudoku.regionsByCellColumn(8)));
    }

    @Test
    public void testIsCellRowValid() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        for (int i = 0; i < 9; i++) {
            assertTrue(sudoku.isCellRowValid(i));
        }
    }

    @Test
    public void testIsCellColumnValid() throws Exception {
        Sudoku sudoku = new Sudoku(3,validValues);
        for (int i = 0; i < 9; i++) {
            assertTrue(sudoku.isCellColumnValid(i));
        }
    }

    @Test
    public void testIsValid() throws Exception {
        Sudoku sudoku = new Sudoku(3, validValues);
        assertTrue(sudoku.isValid());
    }
}