package sample.fileUtils;

import sample.UIUtils.ProgressCounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TextAsWordOccurrences extends HashMapStore {
    ProgressCounter progressCounter;

    public TextAsWordOccurrences(ProgressCounter progressCounter) {
        this.progressCounter = progressCounter;
    }

    @Override
    public void addLine(String line) {
        String[] words = line.split(" ");
        for(String word : words) {
            if(word.isBlank()) continue;
            this.updateHashmap(word);
        }

        progressCounter.addToTotalAnalyzedSize(this.getByteSizeOfLine(line));
    }
}
