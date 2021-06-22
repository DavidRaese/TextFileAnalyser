package sample.ThreadUtils;

import sample.fileUtils.FileUtil;
import sample.fileUtils.TextStorable;

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
