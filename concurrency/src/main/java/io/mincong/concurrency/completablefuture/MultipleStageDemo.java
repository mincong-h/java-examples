package io.mincong.concurrency.completablefuture;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MultipleStageDemo {

  public static void main(String[] args) {
    var demo = new MultipleStageDemo();
    demo.scenario1();
    demo.scenario2();
  }

  /**
   * In scenario 1, resource creation and deletion are done in the same completion stage. Given a
   * fixed thread pool executor with 2 threads, the executor will complete the tasks of two
   * resources (e.g. 0 and 1) before starting the next stages. Its behavior can be illustrated as
   * follows:
   *
   * <pre>
   *          +-----------------------------------------------------------------> Time
   *          | +------------------------+ +------------------------+
   * Thread 1 | | Creation 0, Deletion 0 | | Creation 2, Deletion 2 |
   *          | +------------------------+ +------------------------+
   * Thread 2 | | Creation 1, Deletion 1 | | Creation 3, Deletion 3 |
   *          | +------------------------+ +------------------------+
   *          |
   * </pre>
   *
   * This is because these stages are queued in the following way:
   *
   * <pre>
   * 0. +------------------------+
   *    | Creation 0, Deletion 0 |
   *    +------------------------+
   * 1. +------------------------+
   *    | Creation 1, Deletion 1 |
   *    +------------------------+
   * 2. +------------------------+
   *    | Creation 2, Deletion 2 |
   *    +------------------------+
   * 3. +------------------------+
   *    | Creation 3, Deletion 3 |
   *    +------------------------+
   * </pre>
   *
   * Console output:
   *
   * <pre>
   * [15:41:18.010681][main] - Scenario 1 started
   * [15:41:18.103478][pool-1-thread-1] - [id=0] New resource: creating
   * [15:41:18.103482][pool-1-thread-2] - [id=1] New resource: creating
   * [15:41:19.107141][pool-1-thread-1] - [id=0] New resource: created
   * [15:41:19.107472][pool-1-thread-1] - [id=0] Old resource: creating
   * [15:41:19.108780][pool-1-thread-2] - [id=1] New resource: created
   * [15:41:19.108929][pool-1-thread-2] - [id=1] Old resource: creating
   * [15:41:20.112909][pool-1-thread-2] - [id=1] Old resource: created
   * [15:41:20.112909][pool-1-thread-1] - [id=0] Old resource: created
   * [15:41:20.113265][pool-1-thread-1] - [id=2] New resource: creating
   * [15:41:20.113452][pool-1-thread-2] - [id=3] New resource: creating
   * [15:41:21.114555][pool-1-thread-1] - [id=2] New resource: created
   * [15:41:21.114555][pool-1-thread-2] - [id=3] New resource: created
   * [15:41:21.114914][pool-1-thread-1] - [id=2] Old resource: creating
   * [15:41:21.115019][pool-1-thread-2] - [id=3] Old resource: creating
   * [15:41:22.119584][pool-1-thread-2] - [id=3] Old resource: created
   * [15:41:22.119584][pool-1-thread-1] - [id=2] Old resource: created
   * [15:41:22.120209][main] - Scenario 1 finished
   * </pre>
   */
  private void scenario1() {
    print("Scenario 1 started");
    var executor = Executors.newFixedThreadPool(2);
    var futures =
        IntStream.range(0, 4)
            .mapToObj(
                i ->
                    CompletableFuture.runAsync(
                        () -> {
                          createNewResource(i);
                          deleteOldResource(i);
                        },
                        executor))
            .toArray(CompletableFuture[]::new);
    try {
      CompletableFuture.allOf(futures).join();
    } finally {
      executor.shutdownNow();
    }
    print("Scenario 1 finished");
  }

  /**
   * In scenario 2, resource creation and deletion are done separately in two completion stages.
   * Given a fixed thread pool executor with 2 threads, the executor will complete the creation of
   * two resources (e.g. 0 and 1) then continue the creation of two other resources (e.g. 2 and 3).
   * Once done, it will start the deletions in the same way.
   *
   * <pre>
   *          +-----------------------------------------------------------------> Time
   *          | +------------+ +------------+ +------------+ +------------+
   * Thread 1 | | Creation 0 | | Creation 2 | | Deletion 0 | | Deletion 2 |
   *          | +------------+ +------------+ +------------+ +------------+
   * Thread 2 | | Creation 1 | | Creation 3 | | Deletion 1 | | Deletion 3 |
   *          | +------------+ +------------+ +------------+ +------------+
   *          |
   * </pre>
   *
   * This is because these stages are queued in the following way:
   *
   * <pre>
   * 0. +------------+
   *    | Creation 0 |
   *    +------------+
   * 1. +------------+
   *    | Creation 1 |
   *    +------------+
   * 2. +------------+
   *    | Creation 2 |
   *    +------------+
   * 3. +------------+
   *    | Creation 3 |
   *    +------------+
   * 4. +------------+
   *    | Deletion 0 |
   *    +------------+
   * 5. +------------+
   *    | Deletion 1 |
   *    +------------+
   * 6. +------------+
   *    | Deletion 2 |
   *    +------------+
   * 7. +------------+
   *    | Deletion 3 |
   *    +-------------+
   * </pre>
   *
   * Console output:
   *
   * <pre>
   * [15:41:22.120374][main] - Scenario 2 started
   * [15:41:22.123783][pool-2-thread-1] - [id=0] New resource: creating
   * [15:41:22.124870][pool-2-thread-2] - [id=1] New resource: creating
   * [15:41:23.126835][pool-2-thread-1] - [id=0] New resource: created
   * [15:41:23.126927][pool-2-thread-2] - [id=1] New resource: created
   * [15:41:23.127811][pool-2-thread-1] - [id=2] New resource: creating
   * [15:41:23.127868][pool-2-thread-2] - [id=3] New resource: creating
   * [15:41:24.132029][pool-2-thread-2] - [id=3] New resource: created
   * [15:41:24.132044][pool-2-thread-1] - [id=2] New resource: created
   * [15:41:24.132439][pool-2-thread-2] - [id=0] Old resource: creating
   * [15:41:24.132452][pool-2-thread-1] - [id=1] Old resource: creating
   * [15:41:25.137506][pool-2-thread-2] - [id=0] Old resource: created
   * [15:41:25.137506][pool-2-thread-1] - [id=1] Old resource: created
   * [15:41:25.137804][pool-2-thread-2] - [id=3] Old resource: creating
   * [15:41:25.138011][pool-2-thread-1] - [id=2] Old resource: creating
   * [15:41:26.138201][pool-2-thread-2] - [id=3] Old resource: created
   * [15:41:26.138340][pool-2-thread-1] - [id=2] Old resource: created
   * [15:41:26.139091][main] - Scenario 2 finished
   * </pre>
   */
  private void scenario2() {
    print("Scenario 2 started");
    var executor = Executors.newFixedThreadPool(2);
    var futures =
        IntStream.range(0, 4)
            .mapToObj(
                i ->
                    CompletableFuture.runAsync(() -> createNewResource(i), executor)
                        .thenRunAsync(() -> deleteOldResource(i), executor))
            .toArray(CompletableFuture[]::new);
    try {
      CompletableFuture.allOf(futures).join();
    } finally {
      executor.shutdownNow();
    }
    print("Scenario 2 finished");
  }

  private void createNewResource(int i) {
    print("[id=" + i + "] New resource: creating");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // ignore
    }
    print("[id=" + i + "] New resource: created");
  }

  private void deleteOldResource(int i) {
    print("[id=" + i + "] Old resource: creating");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // ignore
    }
    print("[id=" + i + "] Old resource: created");
  }

  private static void print(String message) {
    var thread = Thread.currentThread().getName();
    var time = LocalTime.now();
    System.out.println("[" + time + "][" + thread + "] - " + message);
  }
}
