import lombok.Data;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

@Data
class OutputResult {
	private final String text;
	private final long timeTakenMs;
}

@Data
class OutputRequest {
	private final String text;
	private final CompletableFuture<OutputResult> resultFuture;
}

class Outputter extends Thread {

	private static LinkedBlockingQueue<OutputRequest> requests = new LinkedBlockingQueue<>();

	@Override
	public void run() {
		try {
			while (true) {
				long startTime = System.currentTimeMillis();
				OutputRequest request = requests.take();
				System.out.println(request.getText());
				Thread.sleep(1000);
				request.getResultFuture().complete(new OutputResult(request.getText(), System.currentTimeMillis() - startTime));
			}
		} catch (InterruptedException e) {
			e.printStackTrace(); //Interrupted
		}

	}

	public Future<OutputResult> output(String text) throws InterruptedException {
		CompletableFuture<OutputResult> future = new CompletableFuture<>();

		requests.put(new OutputRequest(text, future));

		return future;
	}
}

public class QueueDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Outputter outputter = new Outputter();
		outputter.setDaemon(true);
		outputter.start();

		Future<OutputResult> halloFuture = outputter.output("Hallo");
		Future<OutputResult> weltFuture = outputter.output("Welt");

		System.out.println(weltFuture.get());
		System.out.println(halloFuture.get());

	}
}
