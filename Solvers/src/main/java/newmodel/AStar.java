package newmodel;

/**
 * <p>
 * </p>
 * <p>Created at 2015-04-25 17-31.</p>
 */
public final class AStar {


    public final static class Instance {
        private final Instance parent;
        private final Sudoku sudoku;
        private final Sudoku.Cell least;
        private final int cost;

        public Instance(Instance parent, Sudoku sudoku) {
            this.parent = parent;
            this.sudoku = sudoku;
            Sudoku.Cell least = sudoku.getFirstEmptyCell();
            int cost = (calcGofCell(sudoku, least) + calcHOfCell(sudoku, least));
            for (Sudoku.Cell[] cells : sudoku.getCells()) {
                for (Sudoku.Cell cell : cells) {
                    if (cell.isEmpty()) {
                        int h = calcHOfCell(sudoku, cell);
                        int g = calcGofCell(sudoku, cell);
                        if ((g + h) < cost) {
                            cost = h + g;
                            least = cell;
                        }
                    }
                }
            }
            this.cost = cost;
            this.least = least;
        }

        public Instance(Sudoku sudoku) {
            this(null, sudoku);
        }

        private static int calcHOfCell(Sudoku sudoku, Sudoku.Cell cell) {
            byte[] possibleValues = Instance.getPossibleValuesOfCell(sudoku, cell);
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

    }

}
