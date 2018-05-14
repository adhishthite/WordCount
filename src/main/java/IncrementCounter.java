import java.util.concurrent.atomic.*;

public class IncrementCounter {
	private final AtomicInteger number = new AtomicInteger(0);

	public void getNext() {
		while (number.get() < 100) {
			System.out.println("Thread ID:\t" + Thread.currentThread().getId() + "\tCount:\t" + number.getAndIncrement());
		}
	}

	public static void main(String[] args) {
		final IncrementCounter counter = new IncrementCounter();

		Thread t1 = new Thread() {
			@Override
			public void run() {
				counter.getNext();
			}
		};

		Thread t2 = new Thread() {
			@Override
			public void run() {
				counter.getNext();
			}
		};

		Thread t3 = new Thread() {
			@Override
			public void run() {
				counter.getNext();
			}
		};

		t1.start();
		t2.start();
		t3.start();
	}
}