package FileAnalyser.raese.fileUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import FileAnalyser.raese.UIUtils.ProgressCounter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextAsLetterOccurrencesTest {
    TextAsLetterOccurrences contentStore;

    @AfterEach
    void setup() {
        contentStore = null;
    }


    @Test
    void addLine() {
        contentStore = new TextAsLetterOccurrences(new ProgressCounter(1));
        String testLine = "Hallo das ist ein Test für die Klasse TextAsWordOccurrences.";
        String testLine2 = "Dies ist ein weiterer Testfall";

        contentStore.addLine(testLine);
        assertEquals(3, contentStore.map.get("l"));
        contentStore.addLine(testLine2);
        assertEquals(5, contentStore.map.get("l"));
    }

}