package newmodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-25 17-31.</p>
 */
public final class SudokuSolverAStar {

    private final Sudoku origin;
    private final Set<Instance> closed = new HashSet<>();
    private final Map<Integer, Instance> openedMap = new HashMap<>();
    private final PriorityQueue<Instance> openedPriorityQueue = new PriorityQueue<>();

    public SudokuSolverAStar(Sudoku sudoku) {
        this.origin = sudoku;
    }

    public final Sudoku solve() {
        Instance instance = new Instance(this.origin);
        openedMap.put(instance.hashCode(), instance);
        openedPriorityQueue.add(instance);
        while (!openedPriorityQueue.isEmpty()) {

            instance = openedPriorityQueue.poll();
            openedMap.remove(instance.hashCode());
            if (instance.getSudoku().isValid()) {
                return instance.getSudoku();
            }
            for (Instance child : instance.children()) {
                if (!closed.contains(child)) {
                    if (openedMap.containsKey(child.hashCode())) {
                        if (child.getCost() < openedMap.get(child.hashCode()).getCost()) {
                            openedPriorityQueue.remove(openedMap.get(child.hashCode()));
                            openedPriorityQueue.add(child);
                            openedMap.put(child.hashCode(), child);
                        }
                    } else {
                        openedMap.put(child.hashCode(), child);
                        openedPriorityQueue.add(child);
                    }
                }
            }
        }
        return null;
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
            int g = calcGofCell(sudoku, least);
            int cost = h + g;

            for (Sudoku.Cell[] cells : sudoku.getCells()) {
                for (Sudoku.Cell cell : cells) {
                    if (cell.isEmpty()) {
                        possibleValues = getPossibleValuesOfCell(sudoku, cell);
                        h = calcHOfCell(possibleValues);
                        g = calcGofCell(sudoku, cell);
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

        public static byte[] getPossibleValuesOfCell(Sudoku sudoku, Sudoku.Cell cell) {
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

        public final int[] possibleValues() {
            int[] values = new int[calcHOfCell(this.possibleValues)];
            int j = 0;
            for (int i = 0; i < this.possibleValues.length; i++) {
                if (this.possibleValues[i] != 0) {
                    values[j++] = i;
                }
            }
            return values;
        }

        public final Instance[] children() {
            int[] values = possibleValues();
            Instance[] children = new Instance[values.length];
            for (int j = 0; j < values.length; j++) {
                int value = values[j];
                int row = this.getLeast().getCellRow();
                int column = this.getLeast().getCellColumn();
                Sudoku sudoku = new Sudoku(this.sudoku);
                sudoku.getCells()[row][column] = new Sudoku.Cell(row, column, value);
                children[j] = new Instance(this, sudoku);
            }
            return children;
        }

        @Override
        public final int compareTo(Instance o) {
            return this.getCost() - o.getCost();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Instance instance = (Instance) o;

            return new EqualsBuilder()
                    .append(getCost(), instance.getCost())
                    .append(getParent(), instance.getParent())
                    .append(getSudoku(), instance.getSudoku())
                    .append(getLeast(), instance.getLeast())
                    .append(possibleValues, instance.possibleValues)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(getParent())
                    .append(getSudoku())
                    .append(getLeast())
                    .append(possibleValues)
                    .append(getCost())
                    .toHashCode();
        }
    }

}
