import java.util.*;

public class WordOps {

	/** Split the given line into different words */
	public String[] splitLines(String inputLine) {
		return inputLine.split("[ _\\.,\\-\\+]");
	}

	/** Consider only valid words */
	public String[] checkValidWords(String[] words) {
		List < String > filteredList = new ArrayList < > ();
		for (String word: words) {
			if (word.matches("[a-zA-Z]+")) {
				filteredList.add(word);
			}
		}
		return filteredList.toArray(new String[filteredList.size()]);
	}

	/** Maintain concurrency and either put the word in the Map OR increment the counter if the word exists */
	public synchronized void countWords(Map < String, Integer > counter, String word) {
		if (counter.containsKey(word)) {
			counter.put(word, counter.get(word) + 1);
		} else {
			counter.put(word, 1);
		}
	}
}