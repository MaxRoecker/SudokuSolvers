import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by joao on 4/22/15.
 */
public class Util {
    public static Sudoku readFromFile(String fileName) {
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader fileReader = new BufferedReader(file);
            String lineFirst = fileReader.readLine();
            int lineFrameSudoku = 0;
            Frame[][] frames = new Frame[Sudoku.SUDOKU_FRAME_SIZE][Sudoku.SUDOKU_FRAME_SIZE];
            while (lineFirst != null) {
                String lineSecond = fileReader.readLine();
                String lineThird = fileReader.readLine();
                String[] framesFirstLine = lineFirst.split("-");
                String[] framesSecondLine = lineSecond.split("-");
                String[] framesThirdLine = lineThird.split("-");

                Element[][] elementsFirst = new Element[Frame.FRAME_SIZE][Frame.FRAME_SIZE];
                Element[][] elementsSecond = new Element[Frame.FRAME_SIZE][Frame.FRAME_SIZE];
                Element[][] elementsThird = new Element[Frame.FRAME_SIZE][Frame.FRAME_SIZE];
                for (int frameLine = 0; frameLine < Sudoku.SUDOKU_FRAME_SIZE; frameLine++) {
                    for (int i = 0; i < Frame.FRAME_SIZE; i++) {
                        String[] elementsFirstFrame = framesFirstLine[frameLine].split(" ");
                        for (int k = 0; k < Frame.FRAME_SIZE; k++) {
                            byte value = Byte.parseByte(elementsFirstFrame[k]);
                            Element element;
                            if (Element.EMPTY_VALUE != value) {
                                element = new Element(value, true);
                            } else {
                                element = new Element(Element.EMPTY_VALUE);
                            }
                            elementsFirst[frameLine][i] = element;
                        }
                        String[] elementsSecondFrame = framesSecondLine[frameLine].split(" ");
                        for (int k = 0; k < Frame.FRAME_SIZE; k++) {
                            byte value = Byte.parseByte(elementsSecondFrame[k]);
                            Element element;
                            if (Element.EMPTY_VALUE != value) {
                                element = new Element(value, true);
                            } else {
                                element = new Element(Element.EMPTY_VALUE);
                            }
                            elementsSecond[frameLine][i] = element;
                        }
                        String[] elementsThirdFrame = framesThirdLine[frameLine].split(" ");
                        for (int k = 0; k < Frame.FRAME_SIZE; k++) {
                            byte value = Byte.parseByte(elementsThirdFrame[k]);
                            Element element;
                            if (Element.EMPTY_VALUE != value) {
                                element = new Element(value, true);
                            } else {
                                element = new Element(Element.EMPTY_VALUE);
                            }
                            elementsThird[frameLine][i] = element;
                        }
                    }
                }
                frames[lineFrameSudoku][0] = new Frame(elementsFirst);
                frames[lineFrameSudoku][1] = new Frame(elementsSecond);
                frames[lineFrameSudoku][2] = new Frame(elementsThird);
                lineFrameSudoku++;
                lineFirst = fileReader.readLine();
            }
            return new Sudoku(frames);
        } catch (Exception e) {
            System.out.println("Error! " + e.getMessage());
        }
        return null;
    }
}
