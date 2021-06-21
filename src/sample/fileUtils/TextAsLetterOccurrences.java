package sample.fileUtils;

import sample.UIUtils.ProgressCounter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class TextAsLetterOccurrences extends HashMapStore {
    ProgressCounter progressCounter;
    double bytesAnalyzed = 0;


    public TextAsLetterOccurrences(ProgressCounter progressCounter) {
        this.progressCounter = progressCounter;
    }

    @Override
    public void addLine(String line) {
        byte[] lineAsByteArray;
        String[] letters = line.split("");
        double sizeOfLine = 0;

        try {
            lineAsByteArray = line.getBytes("Cp1252"); //Cp1251 stands for the ANSI encoding
            sizeOfLine = lineAsByteArray.length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for(String letter : letters) {
            if(letter.isBlank()) continue;
            updateHashmap(letter);
        }
        progressCounter.setProgress(bytesAnalyzed += sizeOfLine);
    }

    public void printResults() {
        ArrayList<Result> results = this.getMapEntriesAsListOfResults();
        System.out.println("Hallo");
        for(Result result : results) {
            System.out.println(result.toString());
        }
    }
}
