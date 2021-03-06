package FileAnalyser.raese.fileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

final public class FileUtil {
    private FileUtil() {}

    private static boolean stopRequested = false;

    public static TextStorable readTextFileLineByLine (String filePath, TextStorable textContentStorage) {
        stopRequested = false;
        if(filePath == null || filePath.isBlank())
            throw new IllegalArgumentException("Passed 'String' parameter used as filename must not be 'null' or isBlank()!");
        if(textContentStorage == null)
            throw new IllegalArgumentException("Passed 'TextStorable' parameter must not be null!");

        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = null;
            while (!stopRequested && (line = bufferedReader.readLine()) != null) {
                textContentStorage.addLine(line);
            }
        } catch(IOException exception) {
            System.err.println("Exception during creation or using BufferedReader!");
            exception.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
            } catch (IOException ex) {
                System.err.println("Exception closing BufferedReader!");
                ex.printStackTrace();
            }
        }

        return textContentStorage;
    }

    public static void stopParsing() {
        stopRequested = true;
    }

    public static boolean getStopRequested() {
        return stopRequested;
    }
}
