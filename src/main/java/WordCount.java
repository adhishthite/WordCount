import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;
import com.google.gson.*;

public class WordCount {
    private static final int THREADS = 4;
    private static final Logger LOGGER = Logger.getLogger(WordCount.class.getName());

    public static void main(String[] args) throws Exception {

        LOGGER.info("Logger Name:\t" + LOGGER.getName());

        WordOps wordOps = new WordOps();
        Map<String, Integer> counters = new HashMap<>();
        final Queue<String> wordQueue = new ConcurrentLinkedQueue<>();
        List<Word> wordsList = new ArrayList<Word>();

        new Thread() {
            @Override public void run() {
            		LOGGER.log(Level.INFO, "Thread ID:\t" + Thread.currentThread().getId());
                try {
                    FileOperator fileOperator = new FileOperator(args[0]);
        
                    while(fileOperator.hasNext()) {
                        wordQueue.add(fileOperator.next());
                    }
        
                    fileOperator.close();
                } catch(IOException e) {
                    LOGGER.log(Level.SEVERE, "Exception Occured:\n", e);
                    System.out.println("Exception Occured. Check logs.");
                }
            }
        }.start();

        /** Start the ExecutorService and select a thread from pool to execute */
        ExecutorService es = Executors.newFixedThreadPool(THREADS);
        for ( int i = 0; i < THREADS; i++ ) {
            es.execute(new WordOpsThread(wordOps, counters, wordQueue));
        }
        
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);

        for(String name : counters.keySet()) {
       		wordsList.add(new Word(name, counters.get(name)));
        }

       
       /** Write output to JSON */
        try (Writer writer = new FileWriter("Output-WordCount.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(wordsList, writer);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception Occured:\n", e);
            System.out.println("Exception Occured. Check logs.");
        }
              
       Runtime runtime = Runtime.getRuntime();
       runtime.gc();
       System.out.println("\nTotal Memory Used:\t" + ((runtime.totalMemory() - runtime.freeMemory())/(1024L * 1024L)) + " MB");
    }
}
