package io.mincong.concurrency.completablefuture;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demo for comment 5130660255 in my blog "Why Do We Need Completable Future?":
 * https://mincong.io/2020/06/26/completable-future/#comment-5130660255
 */
public class BlockingThreadDemo {

  /**
   * Possible output of the program:
   *
   * <pre>
   * [20:40:38.195492][main] Started
   * [20:40:38.189831][Thread-0] Started
   * [20:40:38.310267][main] 0
   * [20:40:38.312907][main] 1
   * [20:40:38.313041][main] 2
   * [20:40:38.313145][main] 3
   * [20:40:38.313252][main] 4
   * [20:40:38.313344][main] 5
   * [20:40:38.313429][main] 6
   * [20:40:38.313518][main] 7
   * [20:40:38.313604][main] 8
   * [20:40:38.313700][main] 9
   * [20:40:38.313802][main] Finished
   * [20:40:40.178991][Thread-0] Hello!
   * [20:40:40.179161][Thread-0] Finished
   * </pre>
   *
   * The "main" thread finished around 20:40:38.3 while the "Thread-0" finished around 20:40:40.1.
   * The two seconds delay was caused by the scheduler, which schedule the completion of the future
   * 2 seconds in the future. From this demo, we can see that where the main thread was not blocked
   * by the completable future. Only the worker thread "Thread-0" was blocked.
   */
  public static void main(String[] args) {
    var workerFuture = new CompletableFuture<String>();

    var thread =
        new Thread(
            () -> {
              print("Started");
              final String workerAnswer;
              try {
                workerAnswer = workerFuture.get();
              } catch (Exception e) {
                e.printStackTrace();
                return;
              }
              print(workerAnswer);
              print("Finished");
            });
    thread.start();

    var executor = Executors.newSingleThreadScheduledExecutor();
    try {
      executor.schedule(() -> workerFuture.complete("Hello!"), 2, TimeUnit.SECONDS);

      print("Started");
      for (int i = 0; i < 10; i++) {
        print(i);
      }
      print("Finished");
    } finally {
      executor.shutdown();
    }
  }

  private static void print(Object message) {
    var thread = Thread.currentThread().getName();
    System.out.println("[" + LocalTime.now() + "][" + thread + "] " + message);
  }
}
