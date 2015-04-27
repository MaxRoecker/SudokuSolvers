package util;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by joao on 4/27/15.
 */
public class Util {

    private static int[] readElements(BufferedReader fileReader) {
        try {
            String line = fileReader.readLine();
            int order = Integer.parseInt(line);

            int[] result = new int[order*order*order*order];

            for (int i = 0; i < order * order; i++) {
                line = fileReader.readLine();
                String[] elements = line.split(" ");
                for (int j = 0; j < order*order; j++) {
                    result[(i*order*order) + j] = Integer.parseInt(elements[j]);
                }
            }
            return result;
        } catch (Exception e) {
            System.err.println("Read file error! " + e.getMessage());
        }
        return null;
    }

    public static int[] readFromFile(String fileName) {
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader fileReader = new BufferedReader(file);

            return readElements(fileReader);
        } catch (Exception e) {
            System.err.println("Open File Error! " + e.getMessage());
        }
        return null;
    }
}
