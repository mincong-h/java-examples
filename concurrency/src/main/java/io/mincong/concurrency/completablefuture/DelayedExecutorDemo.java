package io.mincong.concurrency.completablefuture;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Waking up in the morning is difficult... Set an alarm to help you :)
 *
 * <p>The alarm will repeat 3 times every 5 seconds. The output of the program looks like:
 *
 * <pre>
 * 2020-05-24T09:06:40.185209 - Good morning!
 * 2020-05-24T09:06:45.253751 - Wake up please: 1
 * 2020-05-24T09:06:50.260483 - Wake up please: 2
 * 2020-05-24T09:06:55.262777 - Wake up please: 3
 * 2020-05-24T09:06:55.263027 - Finished.
 * </pre>
 */
public class DelayedExecutorDemo {

  public static void main(String[] args) {
    var count = new AtomicInteger(0);
    var executor = CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS);
    var cf = new CompletableFuture<Void>();
    var cf2 =
        cf.thenRunAsync(() -> print("Wake up please: " + count.incrementAndGet()), executor)
            .thenRunAsync(() -> print("Wake up please: " + count.incrementAndGet()), executor)
            .thenRunAsync(() -> print("Wake up please: " + count.incrementAndGet()), executor)
            .whenComplete(
                (ok, ex) -> {
                  if (ex != null) {
                    ex.printStackTrace();
                  } else {
                    print("Finished.");
                  }
                });
    print("Good morning!");
    cf.complete(null);
    cf2.join();
  }

  private static void print(String msg) {
    System.out.println(LocalDateTime.now() + " - " + msg);
  }
}
