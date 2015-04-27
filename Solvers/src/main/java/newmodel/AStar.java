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
        private final PriorityQueue<Slot> slotPriorityQueue = new PriorityQueue<>();

        public Instance(Sudoku sudoku) {
            int order = sudoku.getOrder();
            this.sudoku = sudoku;

            for (Sudoku.Cell[] cells : sudoku.getCells()) {
                for (Sudoku.Cell cell : cells) {
                    Slot slot = new Slot(cell.getCellRow(), cell.getCellColumn(), possibilities(cell), g(cell));
                    this.slots[cell.getCellRow()][cell.getCellColumn()] = slot;
                }
            }

        }

        public byte[] possibilities(Sudoku.Cell cell) {
            byte[] possibilities = new byte[this.sudoku.getOrder() * this.sudoku.getOrder() + 1];
            possibilities[0] = 0;
            for (int i = 1; i < possibilities.length; i++) {
                possibilities[i] = 1;
            }

            for (Sudoku.Cell relation : sudoku.getRowRelations(cell)) {
                possibilities[relation.getValue()] = 0;
            }
            for (Sudoku.Cell relation : sudoku.getColumnRelations(cell)) {
                possibilities[relation.getValue()] = 0;
            }
            for (Sudoku.Cell relation : sudoku.getRegionRelations(cell)) {
                possibilities[relation.getValue()] = 0;
            }
            return possibilities;
        }

        public int g(Sudoku.Cell cell) {
            int counter = 0;
            for (Sudoku.Cell relation : sudoku.getRowRelations(cell)) {
                if (relation.isEmpty())
                    counter++;
            }
            return counter;
        }


    }

    public final static class Slot implements Comparable<Slot>{
        private final int row;
        private final int column;
        private final int g;
        private final byte[] possibilities;

        public Slot(int row, int column, byte[] possibilities, int g) {
            this.row = row;
            this.column = column;
            this.possibilities = possibilities;
            this.g = g;
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

        public int h() {
            int counter = 0;
            for (byte possibility : possibilities)
                counter += possibility;
            return counter;
        }

        public int g() {
            return this.g;
        }

        public int cost() {
            return this.g() + this.h();
        }

        @Override
        public int compareTo(Slot o) {
            return this.cost() - o.cost();
        }
    }

}
