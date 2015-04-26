package newmodel;

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
    private final Region[][] regions;


    public Sudoku(int order, int[] elements) {
        this.order = order;
        this.cells = new Cell[order * order][order * order];
        this.regions = new Region[order][order];

        for (int i = 0; i < this.order; i++) {
            for (int j = 0; j < this.order; j++) {
                Region region = new Region(this, i, j);
                Cell[][] cells = new Cell[this.order][this.order];
                region.setCells(cells);
                this.regions[i][j] = region;
            }
        }

        int k = 0;
        for (int i = 0; i < this.order * this.order; i++) {
            for (int j = 0; j < this.order * this.order; j++) {
                Region region = regions[i / this.order][j / this.order];
                Cell[][] cells = region.getCells();

                Cell cell = new Cell(region, i, j, elements[k++]);
                this.cells[i][j] = cell;

                cells[i % this.order][j % this.order] = cell;
                region.setCells(cells);
            }
        }
    }

    public int getOrder() {
        return order;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Region[][] getRegions() {
        return regions;
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

    public Region[] regionsByRow(int regionRow) {
        return this.regions[regionRow];
    }

    public Region[] regionsByColumn(int regionColumn) {
        Region[] regions = new Region[this.order];
        for (int i = 0; i < this.order; i++) {
            regions[i] = this.regions[i][regionColumn];
        }
        return regions;
    }

    public Region[] regionsByCellRow(int cellRow) {
        return this.regions[cellRow / this.order];
    }

    public Region[] regionsByCellColumn(int cellColumn) {
        Region[] regions = new Region[this.order];
        for (int i = 0; i < this.order; i++) {
            regions[i] = this.regions[i][cellColumn / this.order];
        }
        return regions;
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

    public final boolean isValid(){
        for (Region[] regions : this.regions) {
            for (Region region : regions) {
                if(!region.isValid()){
                    return false;
                }
            }
        }
        for (int i = 0; i < this.order * this.order; i++) {
            if((!isCellColumnValid(i) || (!isCellRowValid(i)))){
                return false;
            }
        }
        return true;
    }

    public String prettyPrint(){
        StringBuilder stringBuilder = new StringBuilder();

        for (Cell[] cells : this.cells) {
            for (Cell cell : cells) {
                stringBuilder.append(cell).append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public static final class Region {
        private final Sudoku sudoku;
        private final int order;
        private final int regionRow;
        private final int regionColumn;
        private Cell[][] cells;

        public Region(Sudoku sudoku, int regionRow, int column) {
            this.sudoku = sudoku;
            this.order = sudoku.getOrder();
            this.regionRow = regionRow;
            this.regionColumn = column;
        }

        public final int getRegionRow() {
            return regionRow;
        }

        public final int getRegionColumn() {
            return regionColumn;
        }

        public final Cell[][] getCells() {
            return cells;
        }

        public final void setCells(Cell[][] cells) {
            this.cells = cells;
        }

        public final int getOrder() {
            return order;
        }

        /**
         * Verify if all the elements in region are different.
         *
         * @return {@code true} if the {@link newmodel.Sudoku.Region} is valid, {@code false} otherwise.
         */
        public final boolean isValid() {
            for (int i = 0; i < this.order * this.order; i++) {
                int originRow = i / this.order;
                int originColumn = i % this.order;
                Cell origin = cells[originRow][originColumn];
                if (origin.isEmpty()) {
                    return false;
                }
                for (int j = i + 1; j < this.order * this.order; j++) {
                    int destRow = j / this.order;
                    int destColumn = j % this.order;
                    Cell dest = cells[destRow][destColumn];
                    if (origin.equals(dest) || (dest.isEmpty())) {
                        return false;
                    }
                }
            }
            return true;
        }


        @Override
        public final boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Region region = (Region) o;

            return new EqualsBuilder()
                    .append(getRegionRow(), region.getRegionRow())
                    .append(getRegionColumn(), region.getRegionColumn())
                    .isEquals();
        }

        @Override
        public final int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(getRegionRow())
                    .append(getRegionColumn())
                    .toHashCode();
        }
    }

    public static final class Cell {
        public static final int EMPTY_VALUE = 0;

        private final int cellRow;
        private final int cellColumn;
        private final boolean fixed;
        private Region region;
        private int value;

        public Cell(int cellRow, int cellColumn, int value) {
            this.cellRow = cellRow;
            this.cellColumn = cellColumn;
            this.value = value;
            this.fixed = this.value != 0;
        }

        public Cell(Region region, int cellRow, int cellColumn, int value) {
            this.region = region;
            this.cellRow = cellRow;
            this.cellColumn = cellColumn;
            this.value = value;
            this.fixed = this.value != 0;
        }

        public final Region getRegion() {
            return region;
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
