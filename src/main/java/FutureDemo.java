import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.String.format;

public class FutureDemo {

	private static SlowCalculator slowCalculator = new SlowCalculator();
	private static ExecutorService executorService = Executors.newCachedThreadPool();

	public static void main(String[] args) {
		int[] numbers = setup(20);

		Instant start = Instant.now();
		List<Future<Integer>> futures = new ArrayList<>();
		for (int i = 0; i < numbers.length - 1; i += 2) {
			final int finalI = i;
			Future<Integer> future = executorService.submit(() -> slowCalculator.add(numbers[finalI], numbers[finalI + 1]));
			futures.add(future);
		}

		int sum = 0;
		for (Future<Integer> integerFuture : futures) {
			try {
				sum += integerFuture.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		// should result in 210
		System.out.println(format("Result: %d", sum));
		System.out.println(format("Took %d seconds", Duration.between(start, Instant.now()).getSeconds()));
	}

	private static int[] setup(int countNumbers) {
		int[] numbers = new int[countNumbers];
		for (int i = 0; i < countNumbers; i++) {
			numbers[i] = i + 1;
		}
		return numbers;
	}
}
