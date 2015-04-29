package newmodel;

import org.apache.commons.lang3.ArrayUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Created by joao on 4/28/15.
 */
public final class SudokuSolverGenetic {

    private Person[] population;
    private Sudoku sudoku;

    public SudokuSolverGenetic(Sudoku sudoku) {
        List<Integer> possibleNumbers = new ArrayList<>();
        for (int i = 0; i < sudoku.getOrder() * sudoku.getOrder(); i++) {
            for (int j = 1; j <= sudoku.getOrder() * sudoku.getOrder(); j++) {
                possibleNumbers.add(j);
            }
        }

        for (Sudoku.Cell[] cells : sudoku.getCells()) {
            for (Sudoku.Cell cell : cells) {
                if (!cell.isEmpty()) {
                    possibleNumbers.remove(possibleNumbers.indexOf(cell.getValue()));
                }
            }
        }

        for (Sudoku.Cell[] cells : sudoku.getCells()) {
            for (Sudoku.Cell cell : cells) {
                if (cell.isEmpty()) {
                    Collections.shuffle(possibleNumbers);
                    cell.setValue(possibleNumbers.remove(0));
                }
            }
        }

        this.sudoku = new Sudoku(sudoku);
        this.population = new Person[this.sudoku.getOrder() * this.sudoku.getOrder()];
        for (int i = 0; i < this.sudoku.getOrder() * this.sudoku.getOrder(); i++) {
            this.population[i] = new Person(this.sudoku.cellsByRow(i));
        }
    }

    public Sudoku getSudoku() {
        return sudoku;
    }

    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    public Person[] getPopulation() {
        return population;
    }

    public void setPopulation(Person[] population) {
        this.population = population;
    }

    public void calculePopulationRanking(double sumOfObjectives) {
        for (Person person : population) {
            person.setRanking((double) person.objective() / sumOfObjectives);
        }
    }

    public static final List<Person> crossOver(Person parent1, Person parent2) {
        int positionOfCut = parent1.getFirstPositionOfDuplicate();
        if (positionOfCut <= 0) {
            positionOfCut = new Random().nextInt(9) + 1;
        }
        Sudoku.Cell[] cellsChildren1 = new Sudoku.Cell[parent1.getCells().length];
        Sudoku.Cell[] cellsChildren2 = new Sudoku.Cell[parent2.getCells().length];

        for (int i = 0; i < positionOfCut; i++) {
            cellsChildren1[i] = parent1.getCells()[i];
            cellsChildren2[i] = parent2.getCells()[i];
        }
        for (int i = positionOfCut; i < cellsChildren1.length; i++) {
            cellsChildren1[i] = parent2.getCells()[i];
            cellsChildren2[i] = parent1.getCells()[i];
        }

        List<Person> childrens = new ArrayList<>();
        childrens.add(new Person(cellsChildren1));
        childrens.add(new Person(cellsChildren2));

        return childrens;
    }

    public final Sudoku solve(Conditions conditions) {
        int cycles = 0;
        int cyclesWithoutConvergence = 0;
        Instant start = Instant.now();
        Instant now = Instant.now();
        long minutes = Duration.between(start, now).toMinutes();

        Random r = new Random();

        while (conditions.check(cycles, cyclesWithoutConvergence, minutes)) {
            double sumOfObjectives = 0;
            for (Person person : this.population) {
                sumOfObjectives += person.objective();
            }

            calculePopulationRanking(sumOfObjectives);

            Score[] scores = new Score[this.population.length];
            double sumRankings = 0;
            for (int i = 0; i < this.population.length; i++) {
                scores[i] = new Score(sumRankings, (sumRankings + this.population[i].getRanking()),this.population[i]);
                sumRankings += this.population[i].getRanking();
            }

            double random1 = r.nextDouble();
            double random2 = r.nextDouble();
            Person parent1 = null;
            Person parent2 = null;

            while (parent1 == null || parent2 == null) {
                for (Score score : scores) {
                    if (score.between(random1)) {
                        parent1 = score.getPerson();
                        break;
                    }
                }

                for (Score score : scores) {
                    if (score.between(random2)) {
                        parent2 = score.getPerson();
                        break;
                    }
                }

                /*
                //TODO VERIFICAR COM O MAX
                if (parent1.equals(parent2)) {
                    parent1 = null;
                }
                */
            }

            List<Person> childrens = crossOver(parent1, parent2);

            Person bestChildren = childrens.get(0);
            for (Person children : childrens) {
                if (children.objective() < bestChildren.objective()){
                    bestChildren = children;
                }
            }

            if (updatePopulation(bestChildren)){
                cyclesWithoutConvergence = 0;
            }
            cycles++;
            cyclesWithoutConvergence++;
            now = Instant.now();
            minutes = Duration.between(start, now).toMinutes();

            /**
             * DEBUG
             *
             */
            System.out.println(mountSodoku(sudoku.getOrder()).prettyPrint());
            System.out.println();
            System.out.println();
        }

        Sudoku solution = mountSodoku(sudoku.getOrder());
        System.out.println(cycles);
        System.out.println(cyclesWithoutConvergence);
        System.out.println(minutes);
        return solution;
    }

    private Sudoku mountSodoku(int order) {
        int[] elements = new int[order*order*order*order];
        int index = 0;
        for (int i = 0; i < this.population.length; i++) {
            for (int j = 0; j < this.population[i].cells.length; j++) {
                elements[index++] = this.population[i].cells[j].getValue();
            }
        }
        return new Sudoku(order,elements);
    }

    private boolean updatePopulation(Person children) {
        Person worst = this.population[0];
        int posWorst = 0;
        for (int i = 1; i < this.population.length; i++) {
            if (worst.objective() < this.population[i].objective()){
                worst = this.population[i];
                posWorst = i;
            }
        }

        this.population[posWorst] = new Person(children.cells);
        return false;
    }

    private static final class Score {
        private final double start;
        private final double end;
        private final Person person;

        public Score(double start, double end, Person person) {
            this.start = start;
            this.end = end;
            this.person = person;
        }

        public final boolean between(double value) {
            return this.start <= value && value < this.end;
        }

        public Person getPerson() {
            return person;
        }

        public double getEnd() {
            return end;
        }

        public double getStart() {
            return start;
        }
    }

    public static final class Conditions {
        private final int cycles;
        private final int cyclesWithoutConvergence;
        private final long time;

        public Conditions(long time, int cyclesWithoutConvergence, int cycles) {
            this.time = time;
            this.cyclesWithoutConvergence = cyclesWithoutConvergence;
            this.cycles = cycles;
        }

        public boolean check(int cycles, int cyclesWithoutConvergence, long minutes) {
            return
                    minutes <= time;
        }
    }

    public static final class Person {
        private final Sudoku.Cell[] cells;
        private double ranking;

        public Person(Sudoku.Cell[] cells) {
            this.cells = cells;
            this.ranking = 0;
        }

        public final int getFirstPositionOfDuplicate() {
            for (int i = 0; i < this.cells.length; i++) {
                for (int j = i; j < this.cells.length; j++) {
                    if (this.cells[i].getValue() == this.cells[j].getValue()) {
                        return j;
                    }
                }
            }
            return -1;
        }

        public double getRanking() {
            return ranking;
        }

        public void setRanking(double ranking) {
            this.ranking = ranking;
        }

        public Sudoku.Cell[] getCells() {
            return cells;
        }

        public int objective() {
            int[] values = new int[cells.length];

            for (int i = 0; i < values.length; i++) {
                boolean haveEquals = false;
                for (int j = i - 1; j >= 0; j--) {
                    if (values[j] == cells[i].getValue()) {
                        haveEquals = true;
                        break;
                    }
                }
                if (!haveEquals) {
                    values[i] = cells[i].getValue();
                } else {
                    values[i] = 0;
                }
            }

            int countZeros = 0;
            for (int i = 0; i < values.length; i++) {
                if (values[i] == 0) {
                    countZeros++;
                }
            }

            return countZeros;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "cells=" + Arrays.toString(cells) +
                    '}';
        }

        public int[] getElements() {
            int[] elements = new int[this.cells.length];
            for (int i = 0; i < this.cells.length; i++) {
                elements[i] = cells[i].getValue();
            }
            return elements;
        }
    }
}
