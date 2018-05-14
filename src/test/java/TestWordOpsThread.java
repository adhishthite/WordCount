import org.junit.*;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestWordOpsThread {

    String text = "HELLO TEST 123 HELLO TEST 123 HELLO TEST";
    WordOps testWordOps = new WordOps();
    Queue<String> wordQueue = new ConcurrentLinkedQueue<>();

    @Test
    public void testRun() {
        wordQueue.add(text);
        Map<String, Integer> counters = new HashMap<>();
        WordOpsThread wordOpsThread = new WordOpsThread(testWordOps, counters, wordQueue);

        wordOpsThread.run();

		assertEquals(2, counters.size());
    }
}
