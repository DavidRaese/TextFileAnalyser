package FileAnalyser.raese.ThreadUtils;

import FileAnalyser.raese.fileUtils.FileUtil;
import FileAnalyser.raese.fileUtils.TextStorable;

import java.io.IOException;

public class AnalyzeRunner implements Runnable {
    String filePath;
    TextStorable contentStorage;

    public AnalyzeRunner(String filePath, TextStorable contentStorage) {
        this.filePath = filePath;
        this.contentStorage = contentStorage;
    }


    @Override
    public void run() {
        FileUtil.readTextFileLineByLine(filePath, contentStorage);
    }
}
