package io.mincong.concurrency.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Demonstrate the exception handling mechanism in Java Completable Future.
 *
 * <p>Example output:
 *
 * <pre>
 * ----- handle -----
 * Recovered from "Oops"
 * ----- whenComplete -----
 * Exception occurred
 * Error: java.lang.RuntimeException: Oops
 * ----- exceptionally (with exception) -----
 * Recovered from "Oops"
 * ----- exceptionally (without exception) -----
 * OK
 * ----- End -----
 * </pre>
 *
 * @author Mincong Huang
 */
public class ExceptionHandlingDemo {

  public static void main(String[] args) {
    /*
     * Case 1: handle(BiFunction<? super T, Throwable, ? extends U> fn)
     *
     * - Has access to success? Yes
     * - Has access to failure? Yes
     * - Can recovery from exception? Yes
     * - Is triggered when stage succeed? Yes
     * - Is triggered when stage failed? Yes
     */
    handle();

    /*
     * Case 2: whenComplete(BiConsumer<? super T, ? super Throwable> action)
     *
     * - Has access to success? Yes
     * - Has access to failure? Yes
     * - Can recovery from exception? No
     * - Is triggered when stage succeed? Yes
     * - Is triggered when stage failed? Yes
     */
    try {
      whenComplete();
    } catch (CompletionException e) {
      /*
       * Previous failure "Oops" is encapsulated by as `CompletionException`
       * and is thrown when `join()` happens.
       */
      System.out.println("Error: " + e.getMessage());
    }

    /*
     * Case 3: exceptionally(Function<Throwable, ? extends T> fn)
     *
     * - Has access to success? No
     * - Has access to failure? Yes
     * - Can recovery from exception? Yes
     * - Is triggered when stage succeed? No
     * - Is triggered when stage failed? Yes
     */
    exceptionally1();
    exceptionally2();

    System.out.println("----- End -----");
  }

  private static void handle() {
    System.out.println("----- handle -----");
    CompletableFuture<String> cf =
        CompletableFuture.<String>failedFuture(new RuntimeException("Oops"))
            .handle(
                (msg, ex) -> {
                  if (ex != null) {
                    return "Recovered from \"" + ex.getMessage() + "\"";
                  } else {
                    return msg;
                  }
                });
    System.out.println(cf.join());
  }

  private static void whenComplete() {
    System.out.println("----- whenComplete -----");
    CompletableFuture<String> cf =
        CompletableFuture.<String>failedFuture(new RuntimeException("Oops"))
            /*
             * `whenComplete` allows you to consume the result of the current
             * completion stage, such as logging the success or failure.
             * However, you cannot modify the result of the stage by returning
             * a new value.
             */
            .whenComplete(
                (msg, ex) -> {
                  if (ex != null) {
                    System.out.println("Exception occurred");
                  } else {
                    System.out.println(msg);
                  }
                  // cannot return value
                });
    System.out.println(cf.join());
  }

  private static void exceptionally1() {
    System.out.println("----- exceptionally (with exception) -----");
    CompletableFuture<String> cf =
        CompletableFuture.<String>failedFuture(new RuntimeException("Oops"))
            .exceptionally(ex -> "Recovered from \"" + ex.getMessage() + "\"");
    System.out.println(cf.join());
  }

  private static void exceptionally2() {
    System.out.println("----- exceptionally (without exception) -----");
    CompletableFuture<String> cf =
        CompletableFuture.completedFuture("OK")
            .exceptionally(
                ex -> {
                  /*
                   * This is not called because `exceptionally` is only called
                   * when an exception happened. It is not the case here.
                   */
                  System.out.println("Handling exception");
                  return "Recovered from \"" + ex.getMessage() + "\"";
                });
    System.out.println(cf.join());
  }
}
