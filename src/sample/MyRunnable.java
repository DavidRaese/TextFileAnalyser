package sample;

import sample.fileUtils.FileUtil;
import sample.fileUtils.TextStorable;

public class MyRunnable implements Runnable {
    String filePath;
    TextStorable contentStorage;

    public MyRunnable(String filePath, TextStorable contentStorage) {
        this.filePath = filePath;
        this.contentStorage = contentStorage;
    }


    @Override
    public void run() {
        FileUtil.readTextFileLineByLine(filePath, contentStorage);
    }
}
