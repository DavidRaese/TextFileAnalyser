package FileAnalyser.raese.fileUtils;

import FileAnalyser.raese.UIUtils.ProgressCounter;

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
