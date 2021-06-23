package FileAnalyser.raese.fileUtils;

public class Result {
    String word;
    Integer occurrences;

    public Result(String word, Integer occurrences) {
        this.word = word;
        this.occurrences = occurrences;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Integer occurrences) {
        this.occurrences = occurrences;
    }

    @Override
    public String toString() {
        return "Result{" +
                "word='" + word + '\'' +
                ", occurrences=" + occurrences +
                '}';
    }
}
