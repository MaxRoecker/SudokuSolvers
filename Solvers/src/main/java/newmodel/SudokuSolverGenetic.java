package newmodel;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Created by joao on 4/28/15.
 */
public final class SudokuSolverGenetic {

    private Item[] population;
    private static int id;

    public SudokuSolverGenetic(Sudoku sudoku, int sizePopulation) {
        this.population = new Item[sizePopulation];
        id = sizePopulation+1;

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


        population[0] = new Item(sudoku,0);
        System.out.println(population[0].objective());
        for (int i = 1; i < sizePopulation; i++) {
            population[i] = new Item(new Sudoku(shuffleSudoku(sudoku)),i);
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
        int cut = random.nextInt(parent1.getSudoku().getOrder() * parent1.getSudoku().getOrder());
        Item children1 = new Item(parent1.sudoku,id++);
        Item children2 = new Item(parent2.sudoku,id++);
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
        }
        return null;
    }

    public Item mutation(Item item) {
        return new Item(new Sudoku(shuffleSudoku(item.getSudoku())),item.id);
    }

    public boolean updatePopulation(Item item) {
        int posWorst = 0;
        for (int i = 1; i < this.population.length; i++) {
            if (this.population[i].objective() > this.population[posWorst].objective()) {
                posWorst = i;
            }
        }
        if (this.population[posWorst].objective() < item.objective()) {
            return false;
        }
        this.population[posWorst] = new Item(item.sudoku,item.id);
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

            children = changeInvalidElements(children);

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
        printPopulation();

        System.out.println("Cycles: " + cycles);
        System.out.println("Cycles Without: "+cyclesWithoutConvergence);
        System.out.println("Seconds: "+seconds);
        Item i = fnFitness();
        System.out.println(i.objective());
        return i.getSudoku();
    }

    private Item changeInvalidElements(Item item) {
        int[] elements = new int[item.getSudoku().getOrder()*item.getSudoku().getOrder()];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = 0;
        }
        for (Sudoku.Cell[] cells : item.getSudoku().getCells()) {
            for (Sudoku.Cell cell : cells) {
                elements[cell.getValue()-1]++;
            }
        }
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] > elements.length){
                for (int j = 0; j < elements.length; j++) {
                    if (elements[j] < elements.length){
                        elements[j]--;
                        elements[i]++;
                        System.out.println("Trocando o valor "+(i+1)+" por "+(j+1));
                        item = changeOnSudoku(item,i+1,j+1);
                    }
                }
            }
        }
        return item;
    }

    private Item changeOnSudoku(Item item, int valueOld, int valueNew) {
        for (Sudoku.Cell[] cells : item.getSudoku().getCells()) {
            for (Sudoku.Cell cell : cells) {
                if (cell.getValue() == valueOld){
                    cell.setValue(valueNew);
                    return item;
                }
            }
        }
        return null;
    }

    private void printPopulation() {
        for (Item item : population) {
            System.out.println(item.objective() + " - "+item.id);
            System.out.println(item.getSudoku().prettyPrint());
            System.out.println();
            System.out.println();
        }
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
            return cycles > this.cycles ||
                    cyclesWithoutConvergence > this.cyclesWithoutConvergence ||
                    seconds > this.seconds;
        }

    }

    public static class Item {
        private final int id;
        private final Sudoku sudoku;
        private double ranking;

        public Item(Sudoku sudoku, int id) {
            this.sudoku = sudoku;
            this.id = id;
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

            Item item = (Item) o;

            return item.id == this.id;

        }
    }
}
