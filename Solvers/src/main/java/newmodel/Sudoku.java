package newmodel;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-24 14-48.</p>
 */
public class Sudoku {

    private final int order;
    private final Cell[][] cells;

    public Sudoku(Sudoku sudoku){
        this.order = sudoku.getOrder();
        this.cells = new Cell[order * order][order * order];

        for (int i = 0; i < order * order; i++) {
            for (int j = 0; j < order * order; j++) {
                this.cells[i][j] = new Cell(sudoku.getCells()[i][j]);
            }
        }
    }

    public Sudoku(int order, int[] elements) {
        this.order = order;
        this.cells = new Cell[order * order][order * order];

        int k = 0;
        for (int i = 0; i < order * order; i++) {
            for (int j = 0; j < order * order; j++) {
                this.cells[i][j] = new Cell(i, j, elements[k++]);
            }
        }
    }

    /**
     * @param cell {@link newmodel.Sudoku.Cell} Cell to get row relations.
     * @return Array of {@link Cell} related cells.
     */
    public Cell[] getRowRelations(Cell cell) {
        Cell[] result = new Cell[this.order * this.order - 1];
        Cell[] cellsByRow = this.cellsByRow(cell.getCellRow());
        for (int i = 0; i < cell.getCellRow(); i++) {
            result[i] = cellsByRow[i];
        }
        for (int i = cell.getCellRow() + 1; i < this.order * this.order; i++) {
            result[i - 1] = cellsByRow[i];
        }
        return result;
    }

    /**
     * @param cell {@link newmodel.Sudoku.Cell} Cell to get column relations.
     * @return Array of {@link Cell} related cells.
     */
    public Cell[] getColumnRelations(Cell cell) {
        Cell[] result = new Cell[this.order * this.order - 1];
        Cell[] cellsByColumn = this.cellsByColumn(cell.getCellColumn());
        for (int i = 0; i < cell.getCellColumn(); i++) {
            result[i] = cellsByColumn[i];
        }
        for (int i = cell.getCellColumn() + 1; i < this.order * this.order; i++) {
            result[i - 1] = cellsByColumn[i];
        }
        return result;
    }

    /**
     * @param cell {@link newmodel.Sudoku.Cell} Cell to get region relations.
     * @return Array of {@link Cell} related cells.
     */
    public Cell[] getRegionRelations(Cell cell) {
        Cell[] result = new Cell[this.order * this.order - 1];
        int indexResult = 0;
        for (int l = (cell.getCellRow() / order) * order; l < (((cell.getCellRow() / order) * order) + order); l++) {
            for (int m = (cell.getCellColumn() / order) * order; m < (((cell.getCellColumn() / order) * order) + order); m++) {
                if (!(l == cell.getCellRow() && m == cell.getCellColumn())) {
                    result[indexResult++] = this.cells[l][m];
                }
            }
        }
        return result;
    }

    public Cell[] getRelations(Cell cell) {
        Cell[] result = this.getRowRelations(cell);
        result = ArrayUtils.addAll(result, this.getColumnRelations(cell));
        Cell[] subRegionRelations = new Cell[(this.order - 1) * (this.order - 1)];
        int index = 0;
        for (int l = (cell.getCellRow() / order) * order; l < (((cell.getCellRow() / order) * order) + order); l++) {
            if (l != cell.getCellRow()) {
                for (int m = (cell.getCellColumn() / order) * order; m < (((cell.getCellColumn() / order) * order) + order); m++) {
                    if (m != cell.getCellColumn()) {
                        subRegionRelations[index++] = this.cells[l][m];
                    }
                }
            }
        }
        return ArrayUtils.addAll(result, subRegionRelations);
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

    public Cell[] cellsRegionByCell(Cell cell) {
        Cell[] cells = new Cell[this.order * this.order];
        int index = 0;
        for (int l = (cell.getCellRow() / order) * order; l < (((cell.getCellRow() / order) * order) + order); l++) {
            for (int m = (cell.getCellColumn() / order) * order; m < (((cell.getCellColumn() / order) * order) + order); m++) {
                cells[index++] = this.cells[l][m];
            }
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
                if (cells[i].getValue() == (cells[j].getValue()))
                    return false;
        return true;
    }

    private boolean isCellRegionValid(Cell cell) {
        Cell[] cells = this.cellsRegionByCell(cell);
        for (int i = 0; i < cells.length; i++) {
            for (int j = i + 1; j < cells.length; j++) {
                if (cells[i].getValue() == cells[j].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public final boolean isValid() {
        for (Cell[] cellRow : this.cells) {
            for (Cell cell : cellRow) {
                if (!isCellColumnValid(cell.getCellColumn()) || !isCellRowValid(cell.getCellRow())) {
                    return false;
                }
            }
        }
        for (int i = 0; i < this.order * this.order; i += this.order) {
            for (int j = 0; j < this.order * this.order; j += this.order) {
                if (!isCellRegionValid(this.cells[i][j])){
                    return false;
                }
            }
        }
        return true;
    }

    public final Cell getFirstNotEmptyCell(){
        for (int i = 0; i < this.order* this.order; i++) {
            for (int j = 0; j < this.order * this.order; j++) {
                if (!this.cells[i][j].isEmpty()){
                    return this.cells[i][j];
                }
            }
        }
        return null;
    }

    public final Cell getFirstEmptyCell(){
        for (int i = 0; i < this.order* this.order; i++) {
            for (int j = 0; j < this.order * this.order; j++) {
                if (this.cells[i][j].isEmpty()){
                    return this.cells[i][j];
                }
            }
        }
        return null;
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

        public Cell(Cell cell) {
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
