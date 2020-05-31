package io.mincong.concurrency.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Timeout asynchronously.
 *
 * <p>Tomasz Nurkiewicz, "Asynchronous Timeouts with CompletableFuture", DZone.
 * https://dzone.com/articles/asynchronous-timeouts
 *
 * @author Mincong Huang
 */
public class AsyncTimeoutDemo {

  /**
   * Adjust this value to see different scenarios.
   *
   * <p>Scenario 1: Set this value to 10 so that the main thread will wait 10 seconds before
   * returning the value "Java". Observe that the program timeouts at 5s because of the dependent
   * completable future {@code timeout}.
   *
   * <pre>
   * Waiting 0s
   * Waiting 1s
   * Waiting 2s
   * Waiting 3s
   * Waiting 4s
   * Timeout. Creating exception
   * Exception in thread "main" java.util.concurrent.CompletionException: java.util.concurrent.TimeoutException: Timeout 5s
   * </pre>
   *
   * <p>Scenario 2: Set this value to 4 so that the main will wait 4 seconds before return the value
   * "Java". Observe that the program executes successfully before the timeout and prints "Hello,
   * Java" in the console.
   *
   * <pre>
   * Waiting 0s
   * Waiting 1s
   * Waiting 2s
   * Waiting 3s
   * Hello, Java
   * </pre>
   */
  private static final int MAX_TRY = 4;

  public static void main(String[] args) {
    new AsyncTimeoutDemo().run();
  }

  private void run() {
    var cf = new CompletableFuture<String>();
    cf.completeAsync(
        () -> {
          int i = 0;
          while (i < MAX_TRY) {
            try {
              System.out.println("Waiting " + i + "s");
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
            i++;
          }
          return "Java";
        });
    var timeout = new CompletableFuture<String>();
    CompletableFuture.runAsync(
        () -> {
          System.out.println("Timeout. Creating exception");
          var ex = new TimeoutException("Timeout");
          timeout.completeExceptionally(ex);
        },
        CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS));
    cf.acceptEither(timeout, this::doSth).join();
  }

  private void doSth(String name) {
    System.out.println("Hello, " + name);
  }
}
