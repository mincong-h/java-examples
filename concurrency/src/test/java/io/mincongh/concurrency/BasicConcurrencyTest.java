package io.mincongh.concurrency;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class BasicConcurrencyTest {

  @Test
  public void testKeyCounter() throws InterruptedException {
    MyCounter myCounter = new MyCounter();
    CountDownLatch countDownLatch = new CountDownLatch(2);
    MyThread t1 = new MyThread(myCounter, countDownLatch);
    MyThread t2 = new MyThread(myCounter, countDownLatch);

    t1.start();
    t2.start();

    countDownLatch.await(3, TimeUnit.SECONDS);

    for (int i = 0; i < 10; i++) {
      assertEquals("i = " + i, 2, myCounter.get(i));
    }
  }

}
