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

        private final Sudoku sudoku;
        private final Slot[][] slots;

        public Instance(Sudoku sudoku) {
            int order = sudoku.getOrder();
            this.sudoku = sudoku;
            this.slots = new Slot[order*order][order*order];

            for(Sudoku.Cell[] cells : sudoku.getCells()){
                for(Sudoku.Cell cell : cells){
                    Slot slot = new Slot()
                }
            }

        }

        public Sudoku.Cell cell() {
            Sudoku.Cell choose = sudoku.getCells()[0][0];

            for (Sudoku.Cell[] cells : sudoku.getCells()) {
                for (Sudoku.Cell cell : cells) {

                }

            }
        }


        public int[] possibilities(Sudoku.Cell cell) {
            Sudoku.Cell[] rowCells = this.sudoku.cellsByRow(cell.getCellRow());
            int[] allPossibilities = new int[this.sudoku.getOrder() * this.sudoku.getOrder()];



            for (int i = 0; i < this.sudoku.getOrder() * this.sudoku.getOrder(); i++) {
                int value = rowCells[i].getValue();
                possibilities[value - 1] = Sudoku.Cell.EMPTY_VALUE;
            }
        }


    }

    public final static class Slot {
        private final int row;
        private final int column;
        private final int[] possibilities;

        public Slot(Slot slot){
            this.row = slot.getRow();
            this.column = slot.getColumn();
            this.possibilities = Arrays.copyOf(slot.getPossibilities(),slot.getPossibilities().length);
        }

        public Slot(Sudoku.Cell cell) {
            this.row = cell.getCellRow();
            this.column = cell.getCellColumn();
            int order = cell.getSudoku().getOrder();
            this.possibilities = new int[order*order];
            for (int i = 0; i < order*order; i++) {

            }
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
