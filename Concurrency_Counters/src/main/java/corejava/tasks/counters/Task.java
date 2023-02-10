package corejava.tasks.counters;

public class Task {

	private int numberOfThreads;

	private int numberOfIterations;

	private int pause;

	private int c1;

	private int c2;

	public Task(int numberOfThreads, int numberOfIterations, int pause) {
		this.numberOfThreads = numberOfThreads;
		this.numberOfIterations = numberOfIterations;
		this.pause = pause;
	}

	public void compare() {
		c1 = c2 = 0;
		Thread[] threads = new Thread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; ++i) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					iterate();
				}
			});
		}
		for (int i = 0; i < numberOfThreads; ++i) {
			threads[i].start();
		}
		for (int i = 0; i < numberOfThreads; ++i) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void compareSync() {
		c1 = c2 = 0;
		Thread[] threads = new Thread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; ++i) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					iterateSync();
				}
			});
		}
		for (int i = 0; i < numberOfThreads; ++i) {
			threads[i].start();
		}
		for (int i = 0; i < numberOfThreads; ++i) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void iterate() {
		for (int i = 0; i < numberOfIterations; ++i) {
			System.out.println("" + (c1 == c2) + " " + c1 + " " + c2);
			++c1;
			try {
				Thread.sleep(pause);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			++c2;
		}
	}
	private synchronized void iterateSync() {
		iterate();
	}

	public static void main(String[] args) {
		Task t = new Task(2, 5, 10);
		t.compare();
		System.out.println("~~~");
		t.compareSync();
	}

}
