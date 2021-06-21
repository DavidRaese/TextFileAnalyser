package sample.fileUtils;

public class TextAsWordOccurrencesSlow extends HashMapStore {
    long timeToSleepInMs;

    public TextAsWordOccurrencesSlow(long timeToSleepInMs) {
        this.timeToSleepInMs = timeToSleepInMs;
    }

    @Override
    public void addLine(String line) {
        String[] words = line.split(" ");
        for(String word : words) {
            if(word.isBlank()) continue;
            updateHashmap(word);
        }

        try {
            Thread.sleep(timeToSleepInMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
