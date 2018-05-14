import java.util.*;
import java.util.concurrent.atomic.*;
import com.google.gson.*;
import java.io.*;

public class IncrementCounter {
	private volatile Integer count = 1;
	private volatile Integer threadIdToRun = 1;
	private Object object = new Object();

		private static List<Count> countList = new ArrayList<>();

		public static class Count {
			private Integer number;
			private Integer threadID;
	
			public Count(Integer number, Integer threadID) {
				this.number = number;
				this.threadID = threadID;
			}
		}

	class Printer implements Runnable {

		private int threadId;

		public Printer(int threadId) {
			super();
			this.threadId = threadId;
		}

		@Override
		public void run() {
			try {
				while (count <= 100) {
					synchronized(object) {
						if (threadId != threadIdToRun) {
							object.wait();
						} else {
							System.out.println("Thread:\t" + threadId + "\tNumber:\t" + count);
							
							countList.add(new Count(count, threadId));
							
							count += 1;

							if (threadId == 1)
								threadIdToRun = 2;
							else if (threadId == 2)
								threadIdToRun = 3;
							else if (threadId == 3)
								threadIdToRun = 1;

							object.notifyAll();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}


	public static void main(String[] args) {
		IncrementCounter testClass = new IncrementCounter();
		Thread t1 = new Thread(testClass.new Printer(1));
		Thread t2 = new Thread(testClass.new Printer(2));
		Thread t3 = new Thread(testClass.new Printer(3));

		t1.start();
		t2.start();
		t3.start();

		try (Writer writer = new FileWriter("Output-Increments.json")) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(countList, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}