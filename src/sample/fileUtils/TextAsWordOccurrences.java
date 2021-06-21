package sample.fileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TextAsWordOccurrences extends HashMapStore {

    @Override
    public void addLine(String line) {
        String[] words = line.split(" ");
        for(String word : words) {
            if(word.isBlank()) continue;
            updateHashmap(word);
        }
    }
}
