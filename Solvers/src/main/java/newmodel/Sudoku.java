package newmodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-24 14-48.</p>
 */
public class Sudoku {

    private final int order;
    private final Cell[][] cells;

    public Sudoku(int order, int[] elements) {
        this.order = order;
        this.cells = new Cell[order * order][order * order];

        int k = 0;
        for (int i = 0; i < order * order; i++) {
            for (int j = 0; j < order * order; j++) {
                this.cells[i][j] = new Cell(i, j, elements[k++]);
            }
        }

        /**
         * TODO Verificar se pode adicionar a si mesmo.
         * Set the region relation to each cell.
         */
        for (int i = 0; i < order * order; i++) {
            for (int j = 0; j < order * order; j++) {
                Cell cell = this.cells[i][j];
                for (int l = (i / order); l < ((i / order) + order); l++) {
                    for (int m = (j / order); m < ((j / order) + order); m++) {
                        cell.getRegionRelations().add(this.cells[l][m]);
                    }
                }


            }
        }

        for (int i = 0; i < order * order; i++) {
            for (int j = 0; j < order * order; j++) {
                Cell cell = this.cells[i][j];

                Cell[] rowRelations = this.cellsByRow(i);
                Cell[] columnRelations = this.cellsByColumn(j);

                cell.setRowRelations(Arrays.asList(rowRelations));
                cell.setColumnRelations(Arrays.asList(columnRelations));
            }
        }
    }


    public int getOrder() {
        return order;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell[] cellsByRow(int cellRow) {
        return this.cells[cellRow];
    }

    public Cell[] cellsByColumn(int cellColumn) {
        Cell[] cells = new Cell[this.order * this.order];
        for (int i = 0; i < this.order * this.order; i++) {
            cells[i] = this.cells[i][cellColumn];
        }
        return cells;
    }

    public boolean isCellRowValid(int cellRow) {
        return isCellArrayValid(cellsByRow(cellRow));
    }

    public boolean isCellColumnValid(int cellColumn) {
        return isCellArrayValid(cellsByColumn(cellColumn));
    }

    private boolean isCellArrayValid(Cell[] cells) {
        for (int i = 0; i < cells.length; i++)
            for (int j = i + 1; j < cells.length; j++)
                if (cells[i].equals(cells[j]))
                    return false;
        return true;
    }

    /**
     * TODO
     * @return
     */
    public final boolean isValid() {
        for (int i = 0; i < this.order * this.order; i++) {
            if ((!isCellColumnValid(i) || (!isCellRowValid(i)))) {
                return false;
            }
        }
        return true;
    }

    public String prettyPrint() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Cell[] cells : this.cells) {
            for (Cell cell : cells) {
                stringBuilder.append(cell).append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public static final class Cell {
        public static final int EMPTY_VALUE = 0;

        private final int cellRow;
        private final int cellColumn;
        private final boolean fixed;
        private int value;

        public Cell(Cell cell){
            this.cellRow = cell.getCellRow();
            this.cellColumn = cell.getCellColumn();
            this.value = cell.getValue();
            this.fixed = cell.isFixed();
        }

        public Cell(int cellRow, int cellColumn, int value) {
            this.cellRow = cellRow;
            this.cellColumn = cellColumn;
            this.value = value;
            this.fixed = this.value != 0;
        }

        public final int getCellRow() {
            return cellRow;
        }

        public final int getCellColumn() {
            return cellColumn;
        }

        public final int getValue() {
            return value;
        }

        public final void setValue(int value) {
            this.value = value;
        }

        public final boolean isFixed() {
            return fixed;
        }

        public final boolean isEmpty() {
            return this.value == EMPTY_VALUE;
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Cell cell1 = (Cell) o;

            return new EqualsBuilder()
                    .append(getCellRow(), cell1.getCellRow())
                    .append(getCellColumn(), cell1.getCellColumn())
                    .append(getValue(), cell1.getValue())
                    .isEquals();
        }

        @Override
        public final int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(getCellRow())
                    .append(getCellColumn())
                    .append(getValue())
                    .toHashCode();
        }

        @Override
        public final String toString() {
            return String.valueOf(value);
        }


    }
}
