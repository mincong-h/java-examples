package io.mincong.ocpjp.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class ScheduledExecutorServiceTest {

  private ScheduledExecutorService service;

  private AtomicInteger sum;

  @Before
  public void setUp() throws Exception {
    service = Executors.newScheduledThreadPool(1);
    sum = new AtomicInteger(0);
  }

  @After
  public void tearDown() throws Exception {
    service.shutdownNow();
  }

  @Test
  public void schedule() throws Exception {
    service.schedule(sum::incrementAndGet, 5L, TimeUnit.MILLISECONDS);
    service.awaitTermination(200L, TimeUnit.MILLISECONDS);

    assertEquals(1, sum.get());
  }

  @Test
  public void scheduleAtFixedRate() throws Exception {
    Runnable task = sum::incrementAndGet;
    ScheduledFuture<?> future = service.scheduleAtFixedRate(task, 0L, 500L, TimeUnit.MILLISECONDS);
    boolean isTerminated = service.awaitTermination(1300L, TimeUnit.MILLISECONDS);
    future.cancel(true);

    assertFalse(isTerminated);
    assertTrue(future.isDone());
    /*
     * Task executed at 3 moments:
     * - 0.0s
     * - 0.5s (0.0s + 0.5s * 1)
     * - 1.0s (0.0s + 0.5s * 2)
     */
    assertEquals(3, sum.get());
  }

  @Test
  public void scheduleWithFixedDelay() throws Exception {
    Runnable task = sum::incrementAndGet;
    ScheduledFuture<?> future = service.scheduleWithFixedDelay(task, 0L, 500L, TimeUnit.MILLISECONDS);
    boolean isTerminated = service.awaitTermination(1300L, TimeUnit.MILLISECONDS);
    future.cancel(true);

    assertFalse(isTerminated);
    assertTrue(future.isDone());
    /*
     * Task executed at 3 moments:
     * -  0.0s
     * - ~0.5s (0.0s + delta * 1 + 0.5s * 1)
     * - ~1.0s (0.0s + delta * 2 + 0.5s * 2)
     */
    assertEquals(3, sum.get());
  }

}
