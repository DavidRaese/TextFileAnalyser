package sample.fileUtils;

import sample.UIUtils.ProgressCounter;

import java.io.UnsupportedEncodingException;

public class TextAsWordOccurrencesSlow extends HashMapStore {
    long timeToSleepInMs;
    ProgressCounter progressCounter;
    double bytesAnalyzed = 0;

    public TextAsWordOccurrencesSlow(long timeToSleepInMs, ProgressCounter progressCounter) {
        this.timeToSleepInMs = timeToSleepInMs;
        this.progressCounter = progressCounter;
    }

    @Override
    public void addLine(String line) {
        String[] words = line.split(" ");
        byte[] lineAsByteArray;
        double sizeOfLine = 0;

        try {
            lineAsByteArray = line.getBytes("Cp1252"); //Cp1251 stands for the ANSI encoding
            sizeOfLine = lineAsByteArray.length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for(String word : words) {
            if(word.isBlank()) continue;
            updateHashmap(word);
        }

        try {
            Thread.sleep(timeToSleepInMs);
        } catch (InterruptedException e) {
            System.out.println("Error: Can't stop this thread I'm Sleeping");
        }
        progressCounter.addToTotalAnalyzedSize(sizeOfLine);
    }
}
