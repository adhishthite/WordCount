import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordOpsThread implements Runnable {
	private WordOps wordOps;
	private Queue < String > wordQueue;
	private Map < String, Integer > counters;

	private static final Logger LOGGER = Logger.getLogger(WordCount.class.getName());

	public WordOpsThread(WordOps wordOps, Map < String, Integer > counters, Queue < String > wordQueue) {
		this.wordOps = wordOps;
		this.wordQueue = wordQueue;
		this.counters = counters;

		LOGGER.info("Logger Name:\t" + LOGGER.getName());
	}

	@Override public void run() {
		long threadId = Thread.currentThread().getId();
		LOGGER.log(Level.INFO, "Thread ID:\t" + threadId);

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