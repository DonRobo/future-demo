import static java.lang.String.format;

public class FutureDemo {

	private static SlowCalculator slowCalculator = new SlowCalculator();

	public static void main(String[] args) {
		int[] numbers = setup(20);

		int sum = 0;
		for (int i = 0; i < numbers.length - 1; i += 2) {
			sum += slowCalculator.add(numbers[i], numbers[i + 1]);
		}

		// should result in 210
		System.out.println(format("Result: %d", sum));
	}

	private static int[] setup(int countNumbers) {
		int[] numbers = new int[countNumbers];
		for (int i = 0; i < countNumbers; i++) {
			numbers[i] = i + 1;
		}
		return numbers;
	}
}