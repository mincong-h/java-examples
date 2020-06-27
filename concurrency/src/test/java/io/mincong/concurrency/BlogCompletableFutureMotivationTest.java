package io.mincong.concurrency;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class BlogCompletableFutureMotivationTest {

  /**
   * This test demonstrates that with {@link java.util.concurrent.Future}, there is no easy way to
   * perform callback action after the completion.
   */
  @Test
  void threadPoolMultiTasks() throws Exception {
    var tasks =
        IntStream.range(0, 5)
            .mapToObj(i -> (Callable<Integer>) () -> i + 1)
            .collect(Collectors.toList());

    var results = new HashSet<Integer>();
    var executor = Executors.newFixedThreadPool(3);
    try {
      var futures = executor.invokeAll(tasks);
      for (var f : futures) {
        int result = f.get();
        // perform callback
        results.add(result);
      }
    } finally {
      executor.shutdownNow();
    }

    assertThat(results).containsExactly(1, 2, 3, 4, 5);
  }

  @Test
  void completableFutureMultiTasks() throws Exception {
    var results = new ConcurrentSkipListSet<>();
    var tasks =
        IntStream.range(0, 5)
            .mapToObj(
                i ->
                    CompletableFuture.completedFuture(i)
                        // perform callback
                        .thenApply(v -> v + 1)
                        .thenAccept(results::add))
            .toArray(CompletableFuture[]::new);

    var cf = CompletableFuture.allOf(tasks);
    cf.get();

    assertThat(results).containsExactly(1, 2, 3, 4, 5);
  }

  @Test
  void anotherExecutor() throws Exception {
    var executor = Executors.newSingleThreadExecutor();
    /*
     * Provide your executor to isolate long-running operation.
     */
    var cf = CompletableFuture.supplyAsync(this::getSthSlow, executor);
    assertThat(cf.get()).isEqualTo(1);
  }

  private int getSthSlow() {
    return 1;
  }

  @Test
  void apply() throws Exception {
    // function
    Function<Integer, String> fn = String::valueOf;
    var s1 = fn.apply(1);
    assertThat(s1).isEqualTo("1");

    // CF
    var s2 = CompletableFuture.completedFuture(1).thenApply(String::valueOf).get();
    assertThat(s2).isEqualTo("1");
  }

  @Test
  void accept() throws Exception {
    // consumer
    Consumer<Integer> consumer = v -> assertThat(v).isEqualTo(1);
    consumer.accept(1);

    // CF
    CompletableFuture.completedFuture(1).thenAccept(consumer).get();
  }
}
