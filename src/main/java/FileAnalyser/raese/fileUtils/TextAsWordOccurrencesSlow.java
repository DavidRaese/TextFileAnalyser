package FileAnalyser.raese.fileUtils;

import FileAnalyser.raese.UIUtils.ProgressCounter;

public class TextAsWordOccurrencesSlow extends HashMapStore {
    long timeToSleepInMs;
    ProgressCounter progressCounter;

    public TextAsWordOccurrencesSlow(long timeToSleepInMs, ProgressCounter progressCounter) {
        this.timeToSleepInMs = timeToSleepInMs;
        this.progressCounter = progressCounter;
    }

    @Override
    public void addLine(String line) {
        String[] words = line.split(" ");

        for(String word : words) {
            if(word.isBlank()) continue;
            this.updateHashmap(word);
        }

        try {
            Thread.sleep(timeToSleepInMs);
        } catch (InterruptedException e) {
            System.out.println("Error: Can't stop this thread I'm Sleeping");
        }
        progressCounter.addToTotalAnalyzedSize(this.getByteSizeOfLine(line));
    }
}
