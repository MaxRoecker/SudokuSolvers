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
                }
            }

        }


        public byte[] possibilities(Sudoku.Cell cell) {
            Sudoku.Cell[] rowCells = this.sudoku.cellsByRow(cell.getCellRow());
            byte[] possibilities = new byte[this.sudoku.getOrder() * this.sudoku.getOrder() + 1];
            possibilities[0] = 0;
            for (int i = 0; i < possibilities.length; i++) {
                possibilities[i] = 1;
            }

            for(Sudoku.Cell relation : sudoku.getRowRelations(cell)){
                possibilities[relation.getValue()] = 0;
            }


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
        private final byte[] possibilities;

        public Slot(Slot slot){
            this.row = slot.getRow();
            this.column = slot.getColumn();
            this.possibilities = Arrays.copyOf(slot.getPossibilities(), slot.getPossibilities().length);
        }

        public Slot(int row, int column, byte[] possibilities) {
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

        public byte[] getPossibilities() {
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

    }

}
