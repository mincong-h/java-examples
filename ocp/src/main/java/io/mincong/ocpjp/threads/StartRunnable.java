package io.mincong.ocpjp.threads;

/**
 * @author Mincong Huang
 */
public class StartRunnable {

  public static void main(String... args) {
    // Anonymous
    Runnable r1 = new Runnable() {
      @Override
      public void run() {
        System.out.println("From anonymous");
      }
    };

    // Lambda
    Runnable r2 = () -> System.out.println("From lambda");

    Thread t1 = new Thread(r1);
    Thread t2 = new Thread(r2);
    t1.start();
    t2.start();
  }

}
