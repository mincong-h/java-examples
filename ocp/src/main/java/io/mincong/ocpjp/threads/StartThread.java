package io.mincong.ocpjp.threads;

/**
 * Different ways to run a thread.
 *
 * @author Mincong Huang
 */
public class StartThread {

  public static void main(String... args) {
    // Extends class `Thread`
    Thread t1 = new Worker();
    t1.start();

    // Use anonymous class
    Thread t2 =
        new Thread() {
          @Override
          public void run() {
            System.out.println("From anonymous");
          }
        };
    t2.start();

    // Use lambda (Java 8)
    Thread t3 = new Thread(() -> System.out.println("From lambda"));
    t3.start();
  }

  /**
   * When you create a thread class by extending class {@link Thread}, you lost the flexibility of
   * inheriting any other class.
   */
  public static class Worker extends Thread {
    @Override
    public void run() {
      System.out.println("From inheritance");
    }
  }
}
