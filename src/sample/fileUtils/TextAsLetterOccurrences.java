package sample.fileUtils;

import java.util.ArrayList;

public class TextAsLetterOccurrences extends HashMapStore {

    @Override
    public void addLine(String line) {
        String[] letters = line.split("");
        for(String letter : letters) {
            if(letter.isBlank()) continue;
            updateHashmap(letter);
        }
    }

    public void printResults() {
        ArrayList<Result> results = this.getMapEntriesAsListOfResults();
        System.out.println("Hallo");
        for(Result result : results) {
            System.out.println(result.toString());
        }
    }
}
