package sample.fileUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class HashMapStore implements TextStorable{
    HashMap<String, Integer> map = new HashMap();

    public void updateHashmap(String newKey) {
        if(map.containsKey(newKey)) {
            map.put(newKey, map.get(newKey) + 1);
        } else {
            map.put(newKey, 1);
        }
    }

    public ArrayList<Result> getMapEntriesAsListOfResults() {
        ArrayList<Result> results = new ArrayList<>();
        String key;
        Integer value;

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            key = String.valueOf(pair.getKey());
            value = Integer.parseInt(String.valueOf(pair.getValue()));
//            System.out.println(pair.getKey() + " = " + pair.getValue());
            results.add(new Result(key, value));
            it.remove(); // avoids a ConcurrentModificationException
        }

        return results;
    }

    public double getByteSizeOfLine(String line) {
        byte[] lineAsByteArray;
        double byteSizeOfLine = 0;

        try {
            lineAsByteArray = line.getBytes("Cp1252"); //Cp1251 stands for the ANSI encoding
            byteSizeOfLine = lineAsByteArray.length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return byteSizeOfLine;
    }
}
