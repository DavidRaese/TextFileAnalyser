package sample.fileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TextAsWordOccurrences implements TextStorable {
    HashMap<String, Integer> wordOccurrencesMap = new HashMap();

    @Override
    public void addLine(String line) {
        String[] words = line.split(" ");
        for(String word : words) {
            if(word.isBlank()) continue;
            updateHashmap(word);
        }
    }

    public ArrayList<Result> getWordOccurrencesAsListOfResults() {
        ArrayList<Result> results = new ArrayList<>();
        String word;
        Integer occurrences;

        Iterator it = wordOccurrencesMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            word = String.valueOf(pair.getKey());
            occurrences = Integer.parseInt(String.valueOf(pair.getValue()));

            results.add(new Result(word, occurrences));
            it.remove(); // avoids a ConcurrentModificationException
        }

        return results;
    }

    private void updateHashmap(String newWord) {
        if(wordOccurrencesMap.containsKey(newWord)) {
            wordOccurrencesMap.put(newWord, wordOccurrencesMap.get(newWord) + 1);
        } else {
            wordOccurrencesMap.put(newWord, 1);
        }
    }


}
