package newmodel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-25 17-31.</p>
 */
public final class SudokuSolverAStar {

    private final Sudoku origin;
    private final Collection<Instance> closed = new ArrayList<>();
    private final PriorityQueue<Instance> opened = new PriorityQueue<>();

    public SudokuSolverAStar(Sudoku sudoku){
        this.origin = sudoku;
    }

    public final Sudoku solve(){
        Instance instance = new Instance(this.origin);
        opened.add(instance);
        while (!opened.isEmpty()){
            instance = opened.poll();

            if(instance.getSudoku().isValid()){
                return instance.getSudoku();
            }




        }


    }

    private Instance[] children(Instance instance){

    }




    public final static class Instance implements Comparable<Instance> {
        private final Instance parent;
        private final Sudoku sudoku;
        private final Sudoku.Cell least;
        private final byte[] possibleValues;
        private final int cost;

        public Instance(Instance parent, Sudoku sudoku) {
            this.parent = parent;
            this.sudoku = sudoku;
            Sudoku.Cell least = sudoku.getFirstEmptyCell();

            byte[] possibleValues = getPossibleValuesOfCell(sudoku, least);
            byte[] leastPossibleValues = possibleValues;
            int h = calcHOfCell(possibleValues);
            int g = calcGofCell(sudoku,least);
            int cost = h + g;

            for (Sudoku.Cell[] cells : sudoku.getCells()) {
                for (Sudoku.Cell cell : cells) {
                    if (cell.isEmpty()) {
                        possibleValues = getPossibleValuesOfCell(sudoku,cell);
                        h = calcHOfCell(possibleValues);
                        g = calcGofCell(sudoku,cell);
                        if ((g + h) < cost) {
                            cost = h + g;
                            least = cell;
                            leastPossibleValues = possibleValues;
                        }
                    }
                }
            }
            this.possibleValues = leastPossibleValues;
            this.cost = cost;
            this.least = least;
        }

        public Instance(Sudoku sudoku) {
            this(null, sudoku);
        }

        public Instance(Instance parent) {
            this(parent, new Sudoku(parent.getSudoku()));
        }

        private static int calcHOfCell(byte[] possibleValues) {
            int counter = 0;
            for (byte possibleValue : possibleValues) {
                counter += possibleValue;
            }
            return counter;
        }

        private static byte[] getPossibleValuesOfCell(Sudoku sudoku, Sudoku.Cell cell) {
            byte[] possibilities = new byte[sudoku.getOrder() * sudoku.getOrder() + 1];
            possibilities[0] = 0;
            for (int i = 1; i < possibilities.length; i++) {
                possibilities[i] = 1;
            }
            for (Sudoku.Cell relation : sudoku.getRelations(cell)) {
                possibilities[relation.getValue()] = 0;
            }
            return possibilities;
        }

        private static int calcGofCell(Sudoku sudoku, Sudoku.Cell cell) {
            int counter = 0;
            for (Sudoku.Cell relation : sudoku.getRelations(cell)) {
                if (relation.isEmpty())
                    counter++;
            }
            return counter;
        }

        public final Instance getParent() {
            return parent;
        }

        public final Sudoku getSudoku() {
            return sudoku;
        }

        public final Sudoku.Cell getLeast() {
            return least;
        }

        public final int getCost() {
            return cost;
        }

        public final int[] possibleValues(){
            int[] values = new int[calcHOfCell(this.possibleValues)];
            int j = 0;
            for (int i = 0; i < this.possibleValues.length; i++) {
                if(this.possibleValues[i] != 0){
                    values[j++] = i;
                }
            }
            return values;
        }

        public final Instance[] children(){
            int[] values = possibleValues();
            Instance[] children = new Instance[values.length];


            for (int i = 0; i < children.length; i++) {
                children[i] = new Instance();
            }

            for(int value : values){

            }



        }

        @Override
        public final int compareTo(Instance o) {
            return this.getCost() - o.getCost();
        }
    }

}
