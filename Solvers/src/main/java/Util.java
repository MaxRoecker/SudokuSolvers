import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by joao on 4/22/15.
 */
public class Util {
    public static String concat3Strings(String str1, String str2, String str3) {
        return str1 + " " + str2 + " " + str3;
    }

    private static String separateInString(BufferedReader fileReader) {
        try {
            String line1 = fileReader.readLine();
            String line2 = fileReader.readLine();
            String line3 = fileReader.readLine();
            String result = "";
            while (line1 != null && line2 != null && line3 != null) {

                String[] line1Frames = line1.split(" - ");
                String[] line2Frames = line2.split(" - ");
                String[] line3Frames = line3.split(" - ");

                for (int i = 0; i < Sudoku.SUDOKU_FRAME_SIZE; i++) {
                    result += concat3Strings(line1Frames[i], line2Frames[i], line3Frames[i]) + " - ";
                }


                line1 = fileReader.readLine();
                line2 = fileReader.readLine();
                line3 = fileReader.readLine();
            }
            result = result.substring(0, result.lastIndexOf(" - "));
            return result;
        } catch (Exception e) {
            System.out.println("Read file error! " + e.getMessage());
        }
        return null;
    }

    public static Frame[][] readFromFile(String fileName) {
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader fileReader = new BufferedReader(file);

            String framesLine = separateInString(fileReader);

            String[] framesStr = framesLine.split(" - ");
            Frame[][] frames = new Frame[Sudoku.SUDOKU_FRAME_SIZE][Sudoku.SUDOKU_FRAME_SIZE];

            for (int i = 0; i < framesStr.length; i++) {
                int frameLine = i / Sudoku.SUDOKU_FRAME_SIZE;
                int frameColumn = i % Sudoku.SUDOKU_FRAME_SIZE;

                String[] elementsStr = framesStr[i].split(" ");
                Element[][] elements = new Element[Frame.FRAME_SIZE][Frame.FRAME_SIZE];

                for (int j = 0; j < elementsStr.length; j++) {
                    int elementLine = j / Frame.FRAME_SIZE;
                    int elementColumn = j % Frame.FRAME_SIZE;
                    byte number = Byte.parseByte(elementsStr[j]);
                    Element element;
                    if (number == Element.EMPTY_VALUE) {
                        element = new Element(number);
                    } else {
                        element = new Element(number, true);
                    }

                    elements[elementLine][elementColumn] = element;
                }

                frames[frameLine][frameColumn] = new Frame(elements.clone());
            }
            return frames;
        } catch (Exception e) {
            System.out.println("Open File Error! " + e.getMessage());
        }
        return null;
    }
}
