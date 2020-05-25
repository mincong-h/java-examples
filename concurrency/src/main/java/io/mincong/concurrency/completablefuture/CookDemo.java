package io.mincong.concurrency.completablefuture;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;

/**
 * Cooking is difficult and time-consuming. If you were hungry but still want to a good meal,
 * completable future can help you :)
 *
 * <p>In this demo, we are going to prepare some noodles with vegetables and meat.
 */
public class CookDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("Slow Scenario");
    runSlowScenario();

    System.out.println("---");

    System.out.println("Fast Scenario");
    runFastScenario();
  }

  /**
   * In a slow scenario, tasks are done sequentially in one single thread (main thread).
   *
   * <p>Only do this when you are not hungry. Example output:
   *
   * <pre>
   * 22:01:46.828931 Preparing vegetables
   * 22:01:49.875001 Preparing meat
   * 22:01:52.877546 Boiling water
   * 22:02:02.881440 Preparing noodles
   * 22:02:06.882840 Mixing everything
   * 22:02:07.884371 Finished, duration=21s
   * </pre>
   */
  private static void runSlowScenario() throws Exception {
    var start = Instant.now();

    System.out.println(LocalTime.now() + " - Cutting vegetables");
    Thread.sleep(3_000);

    System.out.println(LocalTime.now() + " - Cutting meat");
    Thread.sleep(3_000);

    System.out.println(LocalTime.now() + " - Boiling water");
    Thread.sleep(10_000);

    System.out.println(LocalTime.now() + " - Cooking noodles");
    Thread.sleep(4_000);

    System.out.println(LocalTime.now() + " - Mixing everything");
    Thread.sleep(1_000);

    var duration = Duration.between(start, Instant.now());
    System.out.println(LocalTime.now() + " - Finished, duration=" + duration.toSeconds() + "s");
  }

  /**
   * In a fast scenario, tasks are handled asynchronously in different completable futures via
   * different threads.
   *
   * <p>On one side, one completable future prepares noodles (boiling water and cooking noodles); on
   * the other side, another completable future prepares vegetables and meat. Preparing noodles
   * takes about 14 seconds (10s + 4s) and preparing vegetables and meat takes about 6 seconds (3s +
   * 3s). Since these tasks are done in parallel, it only takes about 14 seconds to get it done. It
   * improves significantly the execution time. When both tasks are done, noodles will be mixed with
   * vegetables and meat before marking as finished. So it takes about 15s in total. Example output:
   *
   * <pre>
   * 22:20:05.934505 - Cutting vegetables
   * 22:20:05.935236 - Boiling water
   * 22:20:08.982786 - Cutting meat
   * 22:20:15.985791 - Cooking noodles
   * 22:20:19.988903 - Mixing everything
   * 22:20:20.992947 - Finished, duration=15s
   * </pre>
   */
  private static void runFastScenario() {
    var start = Instant.now();
    var noodles =
        CompletableFuture.runAsync(
                () -> {
                  System.out.println(LocalTime.now() + " - Boiling water");
                  sleep(10_000);
                })
            .thenRunAsync(
                () -> {
                  System.out.println(LocalTime.now() + " - Cooking noodles");
                  sleep(4_000);
                });
    var vegetablesAndMeat =
        CompletableFuture.runAsync(
                () -> {
                  System.out.println(LocalTime.now() + " - Cutting vegetables");
                  sleep(3_000);
                })
            .thenRun(
                () -> {
                  System.out.println(LocalTime.now() + " - Cutting meat");
                  sleep(3_000);
                });
    noodles
        .thenCompose(ignored -> vegetablesAndMeat)
        .thenRunAsync(
            () -> {
              System.out.println(LocalTime.now() + " - Mixing everything");
              sleep(1_000);
            })
        .whenComplete(
            (ok, ex) -> {
              if (ex != null) {
                ex.printStackTrace();
              } else {
                var duration = Duration.between(start, Instant.now());
                var msg = LocalTime.now() + " - Finished, duration=" + duration.toSeconds() + "s";
                System.out.println(msg);
              }
            })
        .join();
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      // ignore
    }
  }
}
