package io.mincong.concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.*;

/**
 * @author Mincong Huang
 * @see java.util.concurrent.CompletableFuture
 * @see java.util.concurrent.CompletionStage
 */
class CompletableFutureTest {

  /*
   * ----- Completed API -----
   *
   * Factory methods used for creating a completed completable future
   * or stage:
   *   - Success: completed{Future,Stage}
   *   - Failure: failed{Future,Stage}
   */

  @Test
  void completedFuture() throws Exception {
    var future = CompletableFuture.completedFuture("Hello, Java");
    assertThat(future.isDone()).isTrue();
    assertThat(future.get()).isEqualTo("Hello, Java");
    assertThat(future.isCompletedExceptionally()).isFalse();
  }

  @Test
  void completedStage() throws Exception {
    var future = CompletableFuture.completedStage("Hello, Java").toCompletableFuture();
    assertThat(future.isDone()).isTrue();
    assertThat(future.get()).isEqualTo("Hello, Java");
    assertThat(future.isCompletedExceptionally()).isFalse();
  }

  @Test
  void failedFuture() {
    var future = CompletableFuture.failedFuture(new IllegalArgumentException());
    assertThat(future.isDone()).isTrue();
    assertThatThrownBy(future::get).isInstanceOf(ExecutionException.class);
    assertThat(future.isCompletedExceptionally()).isTrue();
  }

  @Test
  void failedStage() {
    var future =
        CompletableFuture.failedStage(new IllegalArgumentException()).toCompletableFuture();
    assertThat(future.isDone()).isTrue();
    assertThatThrownBy(future::get).isInstanceOf(ExecutionException.class);
    assertThat(future.isCompletedExceptionally()).isTrue();
  }

  /* ----- Complete APIs ----- */

  @Test
  void complete() throws Exception {
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
  void completeExceptionally() throws Exception {
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
  void completeAsync() throws Exception {
    var latch = new CountDownLatch(1);
    var future = new CompletableFuture<String>();

    submit(
        () ->
            future.completeAsync(
                () -> {
                  latch.countDown();
                  return "Hello, Java";
                }));

    latch.await(1, SECONDS);
    assertThat(future.get()).isEqualTo("Hello, Java");
  }

  /* ----- exceptionally API ----- */

  @Test
  void exceptionally() throws Exception {
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

  /* ----- handle APIs ----- */

  @Test
  void handle_failedStage() {
    var future =
        // method `failedStage` returns a CompletableStage
        CompletableFuture.<String>failedStage(new IllegalArgumentException("Oops"))
            .handle((str, ex) -> ex == null ? str : ex.getMessage())
            .toCompletableFuture();
    assertThat(future.join()).isEqualTo("Oops");
  }

  @Test
  void handle_failedFuture() {
    var future =
        // method `failedFuture` returns CompletableFuture
        CompletableFuture.<String>failedFuture(new IllegalArgumentException("Oops"))
            .handle((str, ex) -> ex == null ? str : ex.getMessage())
            .toCompletableFuture();
    assertThat(future.join()).isEqualTo("Oops");
  }

  @Test
  void handle_success() {
    var future =
        CompletableFuture.completedFuture("ok")
            .handle((str, ex) -> ex == null ? str : ex.getMessage())
            .toCompletableFuture();
    assertThat(future.join()).isEqualTo("ok");
  }

  @Test
  void handleAsync() {
    var future =
        CompletableFuture.<String>failedStage(new IllegalArgumentException("Oops"))
            /*
             * Handle the result (success or failure) using the default
             * asynchronous execution facility. It avoids blocking the current
             * thread for waiting too long.
             *
             * @see https://blog.krecan.net/2013/12/25/completablefutures-why-to-use-async-methods/
             */
            .handleAsync((str, ex) -> ex == null ? str : ex.getMessage())
            .toCompletableFuture();
    assertThat(future.join()).isEqualTo("Oops");
  }

  /* ----- get API ----- */

  @Test
  void get_successful() throws Exception {
    var future = new CompletableFuture<String>();
    submit(() -> future.complete("Hello, Java"));
    assertThat(future.get()).isEqualTo("Hello, Java");
  }

  @Test
  void get_timeout() {
    var future = new CompletableFuture<String>();
    assertThatThrownBy(() -> future.get(1, MILLISECONDS)).isInstanceOf(TimeoutException.class);
  }

  /* ----- then{Action} APIs ----- */

  @Test
  void thenAccept() throws Exception {
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
  void thenAcceptBoth() throws Exception {
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
  void thenApply() throws Exception {
    var future1 = new CompletableFuture<String>();
    var future2 = future1.thenApply(s -> "Hello, " + s);

    submit(() -> future1.complete("Java"));

    assertThat(future1.get()).isEqualTo("Java");
    assertThat(future2.get()).isEqualTo("Hello, Java");
  }

  @Test
  void thenCompose() throws Exception {
    var future1 = new CompletableFuture<String>();
    var future2 = future1.thenCompose(s -> CompletableFuture.supplyAsync(() -> "Hello, " + s));

    submit(() -> future1.complete("Java"));

    assertThat(future1.get()).isEqualTo("Java");
    assertThat(future2.get()).isEqualTo("Hello, Java");
  }

  @Test
  void thenCombine() throws Exception {
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

  @Test
  void whenComplete() throws Exception {
    var latch = new CountDownLatch(1);
    var success1 = new AtomicInteger(0);
    var failure1 = new AtomicInteger(0);
    var success2 = new AtomicInteger(0);
    var failure2 = new AtomicInteger(0);
    var future1 = new CompletableFuture<String>();
    var future2 = new CompletableFuture<String>();
    future1.whenComplete(
        (s, ex) -> {
          if (ex == null) {
            success1.incrementAndGet();
          } else {
            failure1.incrementAndGet();
          }
        });
    future2.whenComplete(
        (s, ex) -> {
          if (ex == null) {
            success2.incrementAndGet();
          } else {
            failure2.incrementAndGet();
          }
        });
    submit(
        () -> {
          future1.complete("Hello, Java");
          future2.completeExceptionally(new IllegalArgumentException());
          latch.countDown();
        });

    latch.await(1, SECONDS);
    assertThat(success1.get()).isEqualTo(1);
    assertThat(success2.get()).isEqualTo(0);
    assertThat(failure1.get()).isEqualTo(0);
    assertThat(failure2.get()).isEqualTo(1);
  }

  /* ----- Multiple Futures ----- */

  @Test
  void allOf_failed() {
    var f1 = CompletableFuture.completedFuture("F1");
    var f2 = CompletableFuture.failedFuture(new IllegalStateException("F2"));
    var f3 = CompletableFuture.failedFuture(new IllegalStateException("F3"));
    var futures = CompletableFuture.allOf(f1, f2, f3);
    assertThat(futures.isDone()).isTrue();
    assertThat(futures.isCompletedExceptionally()).isTrue();
    assertThatExceptionOfType(ExecutionException.class)
        .isThrownBy(futures::get)
        .withCauseInstanceOf(IllegalStateException.class)
        .withMessageContaining("F2");
  }

  @Test
  void allOf_succeed() throws Exception {
    var f1 = CompletableFuture.completedFuture("F1");
    var f2 = CompletableFuture.completedFuture("F2");
    var futures = CompletableFuture.allOf(f1, f2);
    assertThat(futures.isDone()).isTrue();
    assertThat(futures.isCompletedExceptionally()).isFalse();
    assertThat(futures.get()).isNull();
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
