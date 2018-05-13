import java.util.*;

public class WordOpsThread implements Runnable {
	private WordOps wordOps;
	private Queue < String > wordQueue;
	private Map < String, Integer > counters;


	public WordOpsThread(WordOps wordOps, Map < String, Integer > counters, Queue < String > wordQueue) {
		this.wordOps = wordOps;
		this.wordQueue = wordQueue;
		this.counters = counters;
	}


	@Override public void run() {
		while (!wordQueue.isEmpty()) {
            
            String line = wordQueue.poll();

			if (line != null) {
				String[] words = wordOps.splitLines(line);
				String[] validWords = wordOps.checkValidWords(words);

				for (String word: validWords) {
					wordOps.countWords(counters, word);
				}
			}
		}
	}
}