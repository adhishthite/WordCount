import org.junit.*;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestWordOps {
	String text = "HELLO TEST 123";
	WordOps testWordOps = new WordOps();
	
	@Test
	public void testSplitLines() {
		String[] expected = {"HELLO", "TEST", "123"};
		String[] actual = testWordOps.splitLines(text);

		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testCheckValidWords() {
		String[] expected = {"HELLO", "TEST"};
		String[] actual = testWordOps.checkValidWords(testWordOps.splitLines(text));

		assertArrayEquals(expected, actual);
	}

	@Test
	public void testCountWords() {
		Map<String, Integer> counters = new HashMap<>();
		String[] words = {"HELLO", "TEST"};

		for(String word : words){
			testWordOps.countWords(counters, word);
		}

		assertEquals(2, counters.size());
	}
}
