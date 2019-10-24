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
	private static ExecutorService executor = Executors.newCachedThreadPool();

	public static void main(String[] args) {
		int[] numbers = setup(20);

		Instant start = Instant.now();
		List<Future<Integer>> results = new ArrayList<>();
		for (int i = 0; i < numbers.length - 1; i += 2) {
			final int j = i;
			Future<Integer> result = executor.submit(() -> slowCalculator.add(numbers[j], numbers[j + 1]));
			results.add(result);
		}
		int sum = results.stream().mapToInt(f -> {
			try {
				return f.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).sum();

		// should result in 210
		System.out.println(format("Result: %d", sum));
		System.out.println(format("Took %d seconds", Duration.between(start, Instant.now()).getSeconds()));

		executor.shutdown();
	}

	private static int[] setup(int countNumbers) {
		int[] numbers = new int[countNumbers];
		for (int i = 0; i < countNumbers; i++) {
			numbers[i] = i + 1;
		}
		return numbers;
	}
}
