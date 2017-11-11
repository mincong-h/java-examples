package io.mincongh.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * @author Mincong HUANG
 */
public class MyThread extends Thread {

  private MyCounter keyCounter;
  private CountDownLatch countDownLatch;

  public MyThread(MyCounter keyCounter, CountDownLatch countDownLatch) {
    this.keyCounter = keyCounter;
    this.countDownLatch = countDownLatch;
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      try {
        keyCounter.incrementKey(i);
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
      }
    }
    countDownLatch.countDown();
  }

}
