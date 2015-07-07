package newmodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by joao on 7/6/15.
 */
public class SudokuSolverPsr {

    private Sudoku sudoku;
    private int[][] conflicts;

    public SudokuSolverPsr(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    private void fillSudoku(){
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


    }

    private static int calcHOfCell(byte[] possibleValues) {
        int counter = 0;
        for (byte possibleValue : possibleValues) {
            counter += possibleValue;
        }
        return counter;
    }

    private void calcHs(){
        conflicts = new int[sudoku.getOrder()*sudoku.getOrder()][sudoku.getOrder()*sudoku.getOrder()];
        for (int i = 0; i < conflicts.length; i++) {
            for (int j = 0; j < conflicts.length; j++) {
                conflicts[i][j] = 0;
            }
        }

        for (Sudoku.Cell[] cells : sudoku.getCells()) {
            for (Sudoku.Cell cell : cells) {
                if (cell.isFixed()){
                    conflicts[cell.getCellRow()][cell.getCellColumn()] = 1000;
                } else {
                    conflicts[cell.getCellRow()][cell.getCellColumn()] = calcHOfCell(getPossibleValuesOfCell(sudoku,cell));
                }
            }
        }
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

    public Sudoku solve(){
        fillSudoku();
        calcHs();

        while (!sudoku.isValid()){
            int minorRow = 0;
            int minorCollum = 0;
            for (int i = 0; i < conflicts.length; i++) {
                for (int j = 0; j < conflicts.length; j++) {
                    if ((conflicts[i][j] < conflicts[minorRow][minorCollum]) && (conflicts[i][j] > 0)){
                        minorRow = i;
                        minorCollum = j;
                    }
                }
            }

            Sudoku.Cell cell = sudoku.getCells()[minorRow][minorCollum];
            byte[] possibilites = getPossibleValuesOfCell(sudoku,cell);
            boolean changed = false;
            for (int i = 0; i < possibilites.length; i++) {
                if (possibilites[i] == 1){
                    cell.setValue(i);
                    changed = true;
                    break;
                }
            }
            if (!changed){
                int row = new Random().nextInt(sudoku.getOrder()*sudoku.getOrder());
                int col = new Random().nextInt(sudoku.getOrder()*sudoku.getOrder());
                while (sudoku.getCells()[row][col].isFixed()){
                    row = new Random().nextInt(sudoku.getOrder()*sudoku.getOrder());
                    col = new Random().nextInt(sudoku.getOrder()*sudoku.getOrder());
                }
                int aux = sudoku.getCells()[minorRow][minorCollum].getValue();
                sudoku.getCells()[minorRow][minorCollum].setValue(sudoku.getCells()[row][col].getValue());
                sudoku.getCells()[row][col].setValue(aux);

                calcHs();
                System.out.println("TROCOU");
                System.out.println();
                continue;
            }
            for (byte possibilite : possibilites) {
                System.out.print(possibilite + " ");
            }
            System.out.println();
            for (int[] conflict : conflicts) {
                for (int i : conflict) {
                    System.out.print(i + " ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("l: "+minorRow+" - c: "+minorCollum);
            System.out.println(sudoku.prettyPrint());
            System.out.println();
            System.out.println();

            calcHs();
        }

        return sudoku;
    }


}
