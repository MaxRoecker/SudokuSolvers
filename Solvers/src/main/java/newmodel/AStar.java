package newmodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

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

        /**
         *
         * @param numbers
         * @return
         */
        private int countZeroValues(int[] numbers){
            int count = 0;
            for(int number : numbers)
                if(number == 0)
                    count++;
            return count;
        }


    }

    public final static class Slot {
        private Sudoku.Cell[] possibilities;

        public Slot(Sudoku.Cell[] possibilities) {
            this.possibilities = possibilities;
        }

        public Sudoku.Cell[] getPossibilities() {
            return possibilities;
        }

        public int h() {
            return 0;
        }
    }

}
