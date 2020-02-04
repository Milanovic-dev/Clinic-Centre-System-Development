package helpers;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;



public class InvokeControl {

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public static void InvokeRepeating(InvokeFunction func, long time, TimeUnit unit)
	{
		final Runnable invoker = new Runnable() {
			public void run() { func.Invoke(); }
		};
		
		final ScheduledFuture<?> handle = scheduler.scheduleAtFixedRate(invoker, 1, time, unit);
		
		if(handle.isDone())
		{
			System.out.println("Invoking finished.");
		}
	}
}
