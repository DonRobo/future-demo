public class SlowCalculator {

	public int add(int a, int b) {
		sleep(1000);
		return a + b;
	}

	private static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
