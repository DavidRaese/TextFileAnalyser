package sample.fileUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import sample.UIUtils.ProgressCounter;

import static org.junit.jupiter.api.Assertions.*;

class TextAsWordOccurrencesTest {
    TextAsWordOccurrences contentStore;

    @AfterEach
    void setup() {
        contentStore = null;
    }

    @Test
    void addLine() {
        contentStore = new TextAsWordOccurrences(new ProgressCounter(1));
        String testLine = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt et accusam et justo duo dolores";
        String testLine2 = "Lorem ipsum ist eine wirklich gute Methode wenn es darum geht dummy Text zu erzeugen. Um zu zeigen, dass sich was verändert: et et etet et  et";

        contentStore.addLine(testLine);
        assertEquals(2, contentStore.map.get("et"));
        contentStore.addLine(testLine2);
        assertEquals(6, contentStore.map.get("et"));
    }

    @Test
    void blanksAreNotAddedByAddLine() {
        contentStore = new TextAsWordOccurrences(new ProgressCounter(1));
        contentStore.addLine(""); // blank
        contentStore.addLine("              "); // tabs
        contentStore.addLine(" "); // space

        assertTrue(contentStore.map.isEmpty());
    }
}