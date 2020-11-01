package io.mincong.concurrency;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/**
 * @author Mincong Huang
 * @blog https://mincong.io/2020/10/25/java-time/
 */
class BlogJavaTimeCompletableFutureTest {

  @Test
  void orTimeout_withInteger() throws Exception {
    var cf = CompletableFuture.completedFuture("hello");
    var timeoutInSeconds = 5;

    var message = cf.orTimeout(timeoutInSeconds, TimeUnit.SECONDS).get();

    assertThat(message).isEqualTo("hello");
  }

  @Test
  void get_withInteger() throws Exception {
    var cf = CompletableFuture.completedFuture("hello");
    var timeoutInSeconds = 5;

    var message = cf.get(timeoutInSeconds, TimeUnit.SECONDS);

    assertThat(message).isEqualTo("hello");
  }

  @Test
  void orTimeout_withJavaDuration() throws Exception {
    var cf = CompletableFuture.completedFuture("hello");
    var timeout = Duration.ofSeconds(5);

    var message = cf.orTimeout(timeout.toSeconds(), TimeUnit.SECONDS).get();

    assertThat(message).isEqualTo("hello");
  }

  @Test
  void get_withJavaDuration() throws Exception {
    var cf = CompletableFuture.completedFuture("hello");
    var timeout = Duration.ofSeconds(5);

    var message = cf.get(timeout.toSeconds(), TimeUnit.SECONDS);

    assertThat(message).isEqualTo("hello");
  }
}
