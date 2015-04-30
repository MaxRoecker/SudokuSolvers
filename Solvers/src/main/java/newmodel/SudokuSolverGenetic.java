package newmodel;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Created by joao on 4/28/15.
 */
public final class SudokuSolverGenetic {

    private Item[] population;

    public SudokuSolverGenetic(Sudoku sudoku, int sizePopulation) {
        this.population = new Item[sizePopulation];

        List<Integer> elementsPossibles = new ArrayList<>();
        for (int i = 0; i < sudoku.getOrder() * sudoku.getOrder(); i++) {
            for (int j = 0; j < sudoku.getOrder() * sudoku.getOrder(); j++) {
                elementsPossibles.add((j + 1));
            }
        }

        for (Sudoku.Cell[] cells : sudoku.getCells()) {
            for (Sudoku.Cell cell : cells) {
                if (!cell.isEmpty()) {
                    elementsPossibles.remove(elementsPossibles.indexOf(cell.getValue()));
                }
            }
        }

        for (Sudoku.Cell[] cells : sudoku.getCells()) {
            for (Sudoku.Cell cell : cells) {
                if (cell.isEmpty()) {
                    cell.setValue(elementsPossibles.remove(0));
                }
            }
        }

        population[0] = new Item(new Sudoku(sudoku));
        for (int i = 1; i < sizePopulation; i++) {
            population[i] = new Item(new Sudoku(shuffleSudoku(sudoku)));
        }
    }

    public Item fnFitness() {
        Item best = this.population[0];
        for (Item item : this.population) {
            if (best.objective() > item.objective()) {
                best = item;
            }
        }
        return best;
    }

    public List<Item> crossOver(Item parent1, Item parent2) {
        Random random = new Random();
        int cut = random.nextInt();
        Item children1 = new Item(parent1.sudoku);
        Item children2 = new Item(parent2.sudoku);
        for (int i = 0; i < cut; i++) {
            for (int j = 0; j < parent1.getSudoku().getOrder() * parent1.getSudoku().getOrder(); j++) {
                children1.getSudoku().getCells()[i][j].setValue(parent1.getSudoku().getCells()[i][j].getValue());
                children2.getSudoku().getCells()[i][j].setValue(parent2.getSudoku().getCells()[i][j].getValue());
            }
        }
        for (int i = cut; i < parent1.getSudoku().getOrder() * parent1.getSudoku().getOrder(); i++) {
            for (int j = 0; j < parent1.getSudoku().getOrder() * parent1.getSudoku().getOrder(); j++) {
                children1.getSudoku().getCells()[i][j].setValue(parent2.getSudoku().getCells()[i][j].getValue());
                children2.getSudoku().getCells()[i][j].setValue(parent1.getSudoku().getCells()[i][j].getValue());
            }
        }
        List<Item> childrens = new ArrayList<>();
        childrens.add(children1);
        childrens.add(children2);
        return childrens;
    }

    public Item reproduce(Item parent1, Item parent2) {
        List<Item> childrens = crossOver(parent1, parent2);
        Item best = childrens.get(0);
        for (Item children : childrens) {
            if (best.objective() > children.objective()) {
                best = children;
            }
        }
        return best;
    }

    public Item roulette() {
        //TODO VERIFICAR SOMA DOS RANKINGS
        int sumOfObjectives = 0;
        for (Item item : this.population) {
            sumOfObjectives += item.objective();
        }
        for (Item item : this.population) {
            item.setRanking(((double) item.objective()) / ((double) sumOfObjectives));
        }

        double sumOfRanking = 0;
        Score[] scores = new Score[this.population.length];
        for (int i = 0; i < this.population.length; i++) {
            scores[i] = new Score(sumOfRanking, sumOfRanking + this.population[i].getRanking(), this.population[i]);
            sumOfRanking += this.population[i].getRanking();
        }

        Random r = new Random();
        double raffled = r.nextDouble();
        for (Score score : scores) {
            if (score.between(raffled)) {
                return score.item;
            }
            System.out.println(score.start + " - " + score.end);
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");
        return null;
    }

    public Item mutation(Item item) {
        return new Item(new Sudoku(shuffleSudoku(item.getSudoku())));
    }

    public boolean updatePopulation(Item item) {
        int posWorst = 0;
        for (int i = 1; i < this.population.length; i++) {
            if (this.population[i].objective() > this.population[posWorst].objective()) {
                posWorst = i;
            }
        }
        if (this.population[posWorst].objective() > item.objective()) {
            return false;
        }
        this.population[posWorst] = new Item(item.sudoku);
        return true;
    }

    public Sudoku solve(Conditions conditions) {
        int cycles = 0;
        int cyclesWithoutConvergence = 0;
        Instant start = Instant.now();
        Instant now;
        long seconds;
        Random random = new Random();
        while (true) {
            Item parent1 = roulette();
            Item parent2 = roulette();

            while (parent1.equals(parent2)) {
                parent1 = roulette();
                parent2 = roulette();
            }

            Item children = reproduce(parent1, parent2);

            if (random.nextInt(100) < 1) {
                children = mutation(children);
            }

            if (updatePopulation(children)){
                cyclesWithoutConvergence = 0;
            } else {
                cyclesWithoutConvergence++;
            }
            cycles++;
            now = Instant.now();
            seconds = Duration.between(start, now).getSeconds();

            if (conditions.checkConditios(cycles, cyclesWithoutConvergence, seconds)) {
                break;
            }
        }
        return fnFitness().getSudoku();
    }

    private Sudoku shuffleSudoku(Sudoku sudoku) {
        Random r = new Random();
        int numberOfChanges = r.nextInt(500) + 1;
        for (int j = 0; j < numberOfChanges; j++) {
            Sudoku.Cell cell1 = getRandomCell(sudoku);
            Sudoku.Cell cell2 = getRandomCell(sudoku);
            int aux = cell1.getValue();
            cell1.setValue(cell2.getValue());
            cell2.setValue(aux);
        }
        return sudoku;
    }

    private Sudoku.Cell getRandomCell(Sudoku sudoku) {
        Random r = new Random();
        int row = r.nextInt(sudoku.getOrder() * sudoku.getOrder());
        int col = r.nextInt(sudoku.getOrder() * sudoku.getOrder());
        while (sudoku.getCells()[row][col].isFixed()) {
            row = r.nextInt(sudoku.getOrder() * sudoku.getOrder());
            col = r.nextInt(sudoku.getOrder() * sudoku.getOrder());
        }

        return sudoku.getCells()[row][col];
    }

    private static final class Score {
        final double start;
        final double end;
        final Item item;

        public Score(double start, double end, Item item) {
            this.start = start;
            this.end = end;
            this.item = item;
        }

        public boolean between(double value) {
            return start <= value &&
                    value < end;
        }
    }

    public static final class Conditions {
        private final int cycles;
        private final int cyclesWithoutConvergence;
        private final long seconds;

        public Conditions(int cycles, int cyclesWithoutConvergence, long seconds) {
            this.cycles = cycles;
            this.cyclesWithoutConvergence = cyclesWithoutConvergence;
            this.seconds = seconds;
        }

        public final boolean checkConditios(int cycles, int cyclesWithoutConvergence, long seconds) {
            return cycles <= this.cycles &&
                    cyclesWithoutConvergence <= this.cyclesWithoutConvergence &&
                    seconds <= this.seconds;
        }

    }

    public static class Item {
        private final Sudoku sudoku;
        private double ranking;

        public Item(Sudoku sudoku) {
            this.sudoku = sudoku;
        }

        public final int objective() {
            int result = 0;
            for (Sudoku.Cell[] cells : sudoku.getCells()) {
                for (Sudoku.Cell cell : cells) {
                    for (Sudoku.Cell relation : sudoku.getRelations(cell)) {
                        if (relation.getValue() == cell.getValue()) {
                            result++;
                        }
                    }
                }
            }

            return result;
        }

        public Sudoku getSudoku() {
            return sudoku;
        }

        public double getRanking() {
            return ranking;
        }

        public void setRanking(double ranking) {
            this.ranking = ranking;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Item item = (Item) o;

            if (Double.compare(item.ranking, ranking) != 0) return false;
            return !(sudoku != null ? !sudoku.equals(item.sudoku) : item.sudoku != null);

        }
    }
}
