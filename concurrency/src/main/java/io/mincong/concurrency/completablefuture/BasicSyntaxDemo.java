package io.mincong.concurrency.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * @author Mincong Huang
 * @blog https://mincong.io/2020/06/26/completable-future/
 */
public class BasicSyntaxDemo {
  public static void main(String[] args) {
    new BasicSyntaxDemo().run();
  }

  private void run() {
    CompletableFuture //
        .runAsync(this::doStep1)
        .thenRun(this::doStep2)
        .thenRun(this::doStep3)
        .join();
  }

  private void doStep1() {
    System.out.println("Step 1");
  }

  private void doStep2() {
    System.out.println("Step 2");
  }

  private void doStep3() {
    System.out.println("Step 3");
  }
}
