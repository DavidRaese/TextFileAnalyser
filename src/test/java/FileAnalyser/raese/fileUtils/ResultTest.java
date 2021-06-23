package FileAnalyser.raese.fileUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ResultTest {
    Result resultObj;

    @BeforeEach
    void setup() {
        resultObj = null;
    }

    @Test
    void testCreationOfResultObject() {
        resultObj = new Result("Hallo", 5);

        assertEquals("Hallo", resultObj.getWord());
        assertEquals(5, resultObj.getOccurrences());
    }
}
