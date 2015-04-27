package newmodel;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-25 17-31.</p>
 */
public final class AStar {

    public final static class Instance {

        private final int[] allPossibilities;
        private final Sudoku sudoku;


        public Instance(Sudoku sudoku) {
            this.sudoku = sudoku;
            this.allPossibilities = new int[sudoku.getOrder() * sudoku.getOrder()];
            for (int i = 0; i < sudoku.getOrder() * sudoku.getOrder(); i++)
                allPossibilities[i] = i + 1;
        }

        public Sudoku.Cell cell() {
            Sudoku.Cell choose = sudoku.getCells()[0][0];

            for (Sudoku.Cell[] cells : sudoku.getCells()) {
                for (Sudoku.Cell cell : cells) {

                }

            }
            return null;
        }

        public int[] possibilities(Sudoku.Cell cell) {
            Sudoku.Cell[] rowCells = this.sudoku.cellsByRow(cell.getCellRow());
            int[] possibilities = Arrays.copyOf(this.allPossibilities, this.allPossibilities.length);

            for (int i = 0; i < this.sudoku.getOrder() * this.sudoku.getOrder(); i++) {
                int value = rowCells[i].getValue();
                possibilities[value - 1] = Sudoku.Cell.EMPTY_VALUE;
            }
            return null;
        }


    }

    public final static class Slot {
        private final int row;
        private final int column;
        private final int[] possibilities;

        public Slot(int row, int column, int[] possibilities) {
            this.row = row;
            this.column = column;
            this.possibilities = possibilities;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public int[] getPossibilities() {
            return possibilities;
        }

        /**
         * Remove the possibility value of the slot.
         * @param possibility
         * @return {@code true} if the possibilities was changed, {@code false} otherwise.
         */
        public boolean removePossibility(int possibility){
            boolean result = (this.possibilities[possibility-1] != 0);
            this.possibilities[possibility-1] = 0;
            return result;
        }

        public int h(){
            int zeros = count(this.possibilities,0);
            return this.possibilities.length - zeros;
        }

        private int count(int[] ints, int value){
            int counter = 0;
            for(int i : ints)
                if (i == value) counter++;
            return counter;
        }

    }

}
