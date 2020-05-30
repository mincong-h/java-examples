package io.mincong.concurrency.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;

/**
 * Demonstrate the exception handling mechanism in Java Completable Future.
 *
 * <p>Example output:
 *
 * <pre>
 * ----- handle (CompletableFuture) -----
 * Recovered from "Oops"
 * ----- handle (CompletionStage) -----
 * Recovered from "Oops"
 * ----- whenComplete (CompletableFuture) -----
 * Exception occurred
 * Error: java.lang.RuntimeException: Oops
 * ----- whenComplete (CompletionStage) -----
 * Exception occurred
 * Error: java.lang.RuntimeException: Oops
 * ----- exceptionally with exception (CompletableFuture) -----
 * Recovered from "Oops"
 * ----- exceptionally without exception (CompletableFuture) -----
 * OK
 * ----- exceptionally with exception (CompletionStage) -----
 * Recovered from "Oops"
 * ----- exceptionally without exception (CompletionStage) -----
 * OK
 * ----- End -----
 * </pre>
 *
 * @author Mincong Huang
 */
public class ExceptionHandlingDemo {

  public static void main(String[] args) {
    /*
     * Case 1: handle(BiFunction<? super T, Throwable, ? extends U> fn): { CompletableFuture<U>, CompletionStage<U> }
     *
     * - Has access to success? Yes
     * - Has access to failure? Yes
     * - Can recover from exception? Yes
     * - Is triggered when stage succeed? Yes
     * - Is triggered when stage failed? Yes
     */
    handleCF();
    handleCS();

    /*
     * Case 2: whenComplete(BiConsumer<? super T, ? super Throwable> action): { CompletableFuture<T>, CompletionStage<T> }
     *
     * - Has access to success? Yes
     * - Has access to failure? Yes
     * - Can recover from exception? No
     * - Is triggered when stage succeed? Yes
     * - Is triggered when stage failed? Yes
     */
    try {
      whenCompleteCF();
    } catch (CompletionException e) {
      /*
       * Previous failure "Oops" is encapsulated by as `CompletionException`
       * and is thrown when `join()` happens.
       */
      System.out.println("Error: " + e.getMessage());
    }
    try {
      whenCompleteCS();
    } catch (CompletionException e) {
      /*
       * Previous failure "Oops" is encapsulated by as `CompletionException`
       * and is thrown when `join()` happens.
       */
      System.out.println("Error: " + e.getMessage());
    }

    /*
     * Case 3: exceptionally(Function<Throwable, ? extends T> fn): { CompletableFuture<T>, CompletionStage<T> }
     *
     * - Has access to success? No
     * - Has access to failure? Yes
     * - Can recover from exception? Yes
     * - Is triggered when stage succeed? No
     * - Is triggered when stage failed? Yes
     */
    exceptionallyCF1();
    exceptionallyCF2();
    exceptionallyCS1();
    exceptionallyCS2();

    System.out.println("----- End -----");
  }

  private static void handleCF() {
    System.out.println("----- handle (CompletableFuture) -----");
    CompletableFuture<String> cf0 = CompletableFuture.failedFuture(new RuntimeException("Oops"));
    CompletableFuture<String> cf1 =
        cf0.handle(
            (msg, ex) -> {
              if (ex != null) {
                return "Recovered from \"" + ex.getMessage() + "\"";
              } else {
                return msg;
              }
            });
    System.out.println(cf1.join());
  }

  private static void handleCS() {
    System.out.println("----- handle (CompletionStage) -----");
    CompletionStage<String> cs0 = CompletableFuture.failedStage(new RuntimeException("Oops"));
    CompletionStage<String> cs1 =
        cs0.handle(
            (msg, ex) -> {
              if (ex != null) {
                return "Recovered from \"" + ex.getMessage() + "\"";
              } else {
                return msg;
              }
            });
    System.out.println(cs1.toCompletableFuture().join());
  }

  private static void whenCompleteCF() {
    System.out.println("----- whenComplete (CompletableFuture) -----");
    CompletableFuture<String> cf0 = CompletableFuture.failedFuture(new RuntimeException("Oops"));
    CompletableFuture<String> cf1 =
        /*
         * `whenComplete` allows you to consume the result of the current
         * completion stage, such as logging the success or failure.
         * However, you cannot modify the result of the stage by returning
         * a new value.
         */
        // whenComplete(BiConsumer<? super T, ? super Throwable> action): CompletableFuture<T>
        cf0.whenComplete(
            (msg, ex) -> {
              if (ex != null) {
                System.out.println("Exception occurred");
              } else {
                System.out.println(msg);
              }
              /*
               * Cannot return value because method whenComplete
               * is not designed to translate completion outcomes.
               * It uses bi-consumer as input parameter:
               * BiConsumer<? super T, ? super Throwable> action
               */
            });
    System.out.println(cf1.join());
  }

  private static void whenCompleteCS() {
    System.out.println("----- whenComplete (CompletionStage) -----");
    CompletionStage<String> cs0 = CompletableFuture.failedStage(new RuntimeException("Oops"));
    CompletionStage<String> cs1 =
        // whenComplete(BiConsumer<? super T, ? super Throwable> action): CompletionStage<T>
        cs0.whenComplete(
            (msg, ex) -> {
              if (ex != null) {
                System.out.println("Exception occurred");
              } else {
                System.out.println(msg);
              }
            });
    System.out.println(cs1.toCompletableFuture().join());
  }

  private static void exceptionallyCF1() {
    System.out.println("----- exceptionally with exception (CompletableFuture) -----");
    CompletableFuture<String> cf0 = CompletableFuture.failedFuture(new RuntimeException("Oops"));
    CompletableFuture<String> cf1 =
        cf0.exceptionally(ex -> "Recovered from \"" + ex.getMessage() + "\"");
    System.out.println(cf1.join());
  }

  private static void exceptionallyCF2() {
    System.out.println("----- exceptionally without exception (CompletableFuture) -----");
    CompletableFuture<String> cf0 = CompletableFuture.completedFuture("OK");
    CompletableFuture<String> cf1 =
        cf0.exceptionally(
            ex -> {
              /*
               * This is not called because `exceptionally` is only called
               * when an exception happened. It is not the case here.
               */
              System.out.println("Handling exception");
              return "Recovered from \"" + ex.getMessage() + "\"";
            });
    System.out.println(cf1.join());
  }

  private static void exceptionallyCS1() {
    System.out.println("----- exceptionally with exception (CompletionStage) -----");
    CompletionStage<String> cs0 = CompletableFuture.failedStage(new RuntimeException("Oops"));
    CompletionStage<String> cs1 =
        cs0.exceptionally(ex -> "Recovered from \"" + ex.getMessage() + "\"");
    System.out.println(cs1.toCompletableFuture().join());
  }

  private static void exceptionallyCS2() {
    System.out.println("----- exceptionally without exception (CompletionStage) -----");
    CompletionStage<String> cs0 = CompletableFuture.completedStage("OK");
    CompletionStage<String> cs1 =
        cs0.exceptionally(
            ex -> {
              System.out.println("Handling exception");
              return "Recovered from \"" + ex.getMessage() + "\"";
            });
    System.out.println(cs1.toCompletableFuture().join());
  }
}
