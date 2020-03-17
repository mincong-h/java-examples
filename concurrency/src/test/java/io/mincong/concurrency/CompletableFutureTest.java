package io.mincong.concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Mincong Huang
 * @see java.util.concurrent.CompletableFuture
 */
public class CompletableFutureTest {

  @Test
  public void create_completedFuture() throws Exception {
    var future = CompletableFuture.completedFuture("Hello, Java");
    assertThat(future.get()).isEqualTo("Hello, Java");
  }

  @Test
  public void create_newFuture() throws Exception {
    var future = new CompletableFuture<String>();
    submit(() -> future.complete("Hello, Java"));
    assertThat(future.get()).isEqualTo("Hello, Java");
  }

  @Test
  public void get_successful() throws Exception {
    var future = new CompletableFuture<String>();
    submit(() -> future.complete("Hello, Java"));
    assertThat(future.get()).isEqualTo("Hello, Java");
  }

  @Test
  public void get_timeout() {
    var future = new CompletableFuture<String>();
    assertThatThrownBy(() -> future.get(1, MILLISECONDS)).isInstanceOf(TimeoutException.class);
  }

  @Test
  public void thenAccept() throws Exception {
    var future = new CompletableFuture<String>();
    var sentence = new AtomicReference<String>();
    var latch = new CountDownLatch(1);
    future.thenAccept(
        s -> {
          sentence.set("Hello, " + s);
          latch.countDown();
        });

    submit(() -> future.complete("Java"));

    latch.await(1, SECONDS);
    assertThat(future.get()).isEqualTo("Java");
    assertThat(sentence.get()).isEqualTo("Hello, Java");
  }

  @Test
  public void thenAcceptBoth() throws Exception {
    var latch = new CountDownLatch(1);
    var sentence = new AtomicReference<String>();
    var future1 = new CompletableFuture<String>();
    var future2 = new CompletableFuture<String>();
    var future3 =
        future1.thenAcceptBoth(
            future2,
            (prefix, language) -> {
              sentence.set(prefix + ", " + language);
              latch.countDown();
            });

    submit(
        () -> {
          future1.complete("Hello");
          future2.complete("Java");
        });

    latch.await(1, SECONDS);
    assertThat(future1.get()).isEqualTo("Hello");
    assertThat(future2.get()).isEqualTo("Java");
    assertThat(future3.get()).isNull();
    assertThat(sentence.get()).isEqualTo("Hello, Java");
  }

  @Test
  public void thenApply() throws Exception {
    var future1 = new CompletableFuture<String>();
    var future2 = future1.thenApply(s -> "Hello, " + s);

    submit(() -> future1.complete("Java"));

    assertThat(future1.get()).isEqualTo("Java");
    assertThat(future2.get()).isEqualTo("Hello, Java");
  }

  @Test
  public void thenCompose() throws Exception {
    var future1 = new CompletableFuture<String>();
    var future2 = future1.thenCompose(s -> CompletableFuture.supplyAsync(() -> "Hello, " + s));

    submit(() -> future1.complete("Java"));

    assertThat(future1.get()).isEqualTo("Java");
    assertThat(future2.get()).isEqualTo("Hello, Java");
  }

  @Test
  public void thenCombine() throws Exception {
    var future1 = new CompletableFuture<String>();
    var future2 = new CompletableFuture<String>();
    var future3 = future1.thenCombine(future2, (prefix, language) -> prefix + ", " + language);

    submit(
        () -> {
          future1.complete("Hello");
          future2.complete("Java");
        });

    assertThat(future1.get()).isEqualTo("Hello");
    assertThat(future2.get()).isEqualTo("Java");
    assertThat(future3.get()).isEqualTo("Hello, Java");
  }

  private static void submit(Runnable runnable) {
    Executors.newCachedThreadPool()
        .submit(
            () -> {
              Thread.sleep(100);
              runnable.run();
              return null;
            });
  }
}
