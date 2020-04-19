package io.mincong.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 */
class BasicConcurrencyTest {

  @Test
  void testKeyCounter() throws InterruptedException {
    MyCounter myCounter = new MyCounter();
    CountDownLatch countDownLatch = new CountDownLatch(2);
    MyThread t1 = new MyThread(myCounter, countDownLatch);
    MyThread t2 = new MyThread(myCounter, countDownLatch);

    t1.start();
    t2.start();

    countDownLatch.await(3, TimeUnit.SECONDS);

    for (int i = 0; i < 10; i++) {
      assertThat(myCounter.get(i)).isEqualTo(2);
    }
  }

}
