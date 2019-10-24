import java.util.concurrent.LinkedBlockingQueue;

public class QueueDemo {

	private static LinkedBlockingQueue<String> requests = new LinkedBlockingQueue<>();
	private static Thread outputThread = new Thread(() ->
	{
		try {
			while (true) {
				String output = requests.take();
				System.out.println(output);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	});

	public static void main(String[] args) throws InterruptedException {
		outputThread.start();

		requests.put("Hallo");
		requests.put("Welt");

		outputThread.interrupt();
	}
}
