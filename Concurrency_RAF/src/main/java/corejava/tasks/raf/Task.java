package corejava.tasks.raf;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Task {
	
	public static final String FILE_NAME = "data.txt";
	public static final int SEP_LEN = System.lineSeparator().length();

	private static RandomAccessFile raf;

	public static void createRAF(int numberOfThreads, int numberOfIterations, int pause) {
		try {
			raf = new RandomAccessFile(FILE_NAME, "rw");
			raf.setLength(0); // Чистимо цей файл на випадок, якщо він не був порожнім до runtime

			Thread[] threads = new Thread[numberOfThreads];
			// Обходимо обмеження inner классів через Atomic об'єкти
			for (AtomicInteger i = new AtomicInteger(0); i.get() < numberOfThreads; i.incrementAndGet()) {
				threads[i.get()] = new Thread(new Runnable() {
					// Для кожного потоку необхідно зберігти в окрему змінну номер
					// рядка, який цей потік буде заповнювати, бо ми почиаємо запускати 
					// потоки після того, як ініціалізуємо їх всі
					final int indexOfLine = i.get();
					@Override
					public void run() {
						try {
							for (int j = 0; j < numberOfIterations; ++j) {
								// Файл для запису один на всіх, тому синхроніхуємо код, 
								// що виконує дії над ним, тобто перенесення курсору на
							    // необхідну позицію у файлі (seek) та безпосередньо запис (write)
								syncWrite(j, indexOfLine, numberOfIterations);
								// Після останньго символа, що має записати потік, 
								// одразу після нього записуємо сєпара
								if (j == numberOfIterations - 1) {
									syncWrite(numberOfIterations, indexOfLine, numberOfIterations);
								}
								Thread.sleep(pause);
							}
						} catch (IOException | InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
				});
			}

			// Запускаємо всі потоки
			for (int i = 0; i < numberOfThreads; ++i) {
				threads[i].start();
			}

			// Чекаємо на завершення виконання всіх потоків
			for (int i = 0; i < numberOfThreads; ++i) {
				threads[i].join();
			}
			raf.close();
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static synchronized void syncWrite(int iteration, int idxOfLine, int numberOfIterations) throws IOException {
		raf.seek((long) idxOfLine * (numberOfIterations + SEP_LEN) + iteration);
		if (iteration == numberOfIterations) {
			raf.write(System.lineSeparator().getBytes());
		} else {
			raf.write('0' + idxOfLine);
		}
	}



	public static void main(String[] args) throws IOException {
		createRAF(5, 20, 1000);
		Files.readAllLines(Paths.get(FILE_NAME))
			.stream()
			.forEach(System.out::println);
	}
}
