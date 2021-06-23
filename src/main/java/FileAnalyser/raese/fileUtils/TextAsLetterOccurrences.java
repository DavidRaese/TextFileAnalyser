package FileAnalyser.raese.fileUtils;

import FileAnalyser.raese.UIUtils.ProgressCounter;

import java.util.ArrayList;

public class TextAsLetterOccurrences extends HashMapStore {
    ProgressCounter progressCounter;

    public TextAsLetterOccurrences(ProgressCounter progressCounter) {
        this.progressCounter = progressCounter;
    }

    @Override
    public void addLine(String line) {
        String[] letters = line.split("");

        for(String letter : letters) {
            if(letter.isBlank()) continue;
            this.updateHashmap(letter);
        }

        progressCounter.addToTotalAnalyzedSize(this.getByteSizeOfLine(line));
    }

    public void printResults() {
        ArrayList<Result> results = this.getMapEntriesAsListOfResults();
        for(Result result : results) {
            System.out.println(result.toString());
        }
    }
}
