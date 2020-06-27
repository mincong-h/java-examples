package io.mincong.concurrency;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
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
            .mapToObj(i -> (Callable<Integer>) () -> i)
            .collect(Collectors.toList());

    var results = new HashSet<Integer>();
    var executor = Executors.newFixedThreadPool(3);
    try {
      var futures = executor.invokeAll(tasks);
      for (var f : futures) {
        var result = f.get();
        // perform callback
        results.add(result);
      }
    } finally {
      executor.shutdownNow();
    }

    assertThat(results).containsExactly(1, 2, 3, 4, 5);
  }
}
