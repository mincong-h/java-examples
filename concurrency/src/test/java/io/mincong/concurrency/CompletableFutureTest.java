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
 * @see java.util.concurrent.CompletionStage
 */
public class CompletableFutureTest {

  /*
   * ----- Completed API -----
   *
   * Factory methods used for creating a completed completable future
   * or stage:
   *   - Success: completed{Future,Stage}
   *   - Failure: failed{Future,Stage}
   */

  @Test
  public void completedFuture() throws Exception {
    var future = CompletableFuture.completedFuture("Hello, Java");
    assertThat(future.isDone()).isTrue();
    assertThat(future.get()).isEqualTo("Hello, Java");
    assertThat(future.isCompletedExceptionally()).isFalse();
  }

  @Test
  public void completedStage() throws Exception {
    var future = CompletableFuture.completedStage("Hello, Java").toCompletableFuture();
    assertThat(future.isDone()).isTrue();
    assertThat(future.get()).isEqualTo("Hello, Java");
    assertThat(future.isCompletedExceptionally()).isFalse();
  }

  @Test
  public void failedFuture() {
    var future = CompletableFuture.failedFuture(new IllegalArgumentException());
    assertThat(future.isDone()).isTrue();
    assertThatThrownBy(future::get).isInstanceOf(ExecutionException.class);
    assertThat(future.isCompletedExceptionally()).isTrue();
  }

  @Test
  public void failedStage() {
    var future =
        CompletableFuture.failedStage(new IllegalArgumentException()).toCompletableFuture();
    assertThat(future.isDone()).isTrue();
    assertThatThrownBy(future::get).isInstanceOf(ExecutionException.class);
    assertThat(future.isCompletedExceptionally()).isTrue();
  }

  /* ----- Complete APIs ----- */

  @Test
  public void complete() throws Exception {
    var latch = new CountDownLatch(1);
    var future = new CompletableFuture<String>();

    submit(
        () -> {
          future.complete("Hello, Java");
          latch.countDown();
        });

    latch.await(1, SECONDS);
    assertThat(future.get()).isEqualTo("Hello, Java");
  }

  @Test
  public void completeExceptionally() throws Exception {
    var latch = new CountDownLatch(1);
    var future = new CompletableFuture<String>();

    submit(
        () -> {
          future.completeExceptionally(new IllegalArgumentException());
          latch.countDown();
        });

    latch.await(1, SECONDS);
    assertThat(future.isCompletedExceptionally()).isTrue();
    assertThatThrownBy(future::get).isInstanceOf(ExecutionException.class);
  }

  @Test
  public void completeAsync() throws Exception {
    var latch = new CountDownLatch(1);
    var future = new CompletableFuture<String>();

    submit(
        () -> future.completeAsync(
            () -> {
              latch.countDown();
              return "Hello, Java";
            }));

    latch.await(1, SECONDS);
    assertThat(future.get()).isEqualTo("Hello, Java");
  }

  /* ----- exceptionally API ----- */

  @Test
  public void exceptionally() throws Exception {
    var latch = new CountDownLatch(1);
    var future1 = new CompletableFuture<String>();
    var future2 = future1.exceptionally(ex -> "Enough Java for today");

    submit(
        () -> {
          future1.completeExceptionally(new IllegalArgumentException());
          latch.countDown();
        });

    latch.await(1, SECONDS);
    assertThat(future1.isCompletedExceptionally()).isTrue();
    assertThatThrownBy(future1::get).isInstanceOf(ExecutionException.class);
    assertThat(future2.isCompletedExceptionally()).isFalse();
    assertThat(future2.get()).isEqualTo("Enough Java for today");
  }

  /* ----- get API ----- */

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

  /* ----- then{Action} APIs ----- */

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

  /* ----- Utility ----- */

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
