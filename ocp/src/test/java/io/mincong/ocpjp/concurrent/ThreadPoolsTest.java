package io.mincong.ocpjp.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 *
 * <ul>
 *   <li>Core pool size: the core number of threads.
 *   <li>Max pool size: the maximum allowed number of threads.
 * </ul>
 *
 * @author Mincong Huang
 */
public class ThreadPoolsTest {

  private static final int POOL_SIZE = 2;

  /**
   * The parallelism level corresponds to the maximum number of threads actively engaged in, or
   * available to engage in, task processing.
   */
  private static final int PARALLELISM_LEVEL = 2;

  private ExecutorService fixedThreadPool;

  private ExecutorService cachedThreadPool;

  private ExecutorService singleThreadPool;

  private ScheduledExecutorService scheduledThreadPool;

  private ExecutorService workingStealingPool;

  private AtomicInteger sum;

  @Before
  public void setUp() throws Exception {
    sum = new AtomicInteger(0);

    fixedThreadPool = Executors.newFixedThreadPool(POOL_SIZE);
    cachedThreadPool = Executors.newCachedThreadPool();
    singleThreadPool = Executors.newSingleThreadExecutor();
    scheduledThreadPool = Executors.newScheduledThreadPool(POOL_SIZE);
    workingStealingPool = Executors.newWorkStealingPool(PARALLELISM_LEVEL);
  }

  @After
  public void tearDown() throws Exception {
    fixedThreadPool.shutdownNow();
    cachedThreadPool.shutdownNow();
    singleThreadPool.shutdownNow();
    scheduledThreadPool.shutdownNow();
    workingStealingPool.shutdownNow();
  }

  @Test
  public void fixedThreadPool_submit() throws Exception {
    Future<Integer> resultA = fixedThreadPool.submit(() -> sum.incrementAndGet());
    Future<Integer> resultB = fixedThreadPool.submit(() -> sum.incrementAndGet());
    fixedThreadPool.awaitTermination(1, TimeUnit.SECONDS);

    assertTrue(resultA.isDone());
    assertTrue(resultB.isDone());
    assertEquals(2, sum.get());
  }

  @Test
  public void fixedThreadPool() throws Exception {
    ThreadPoolExecutor executor = (ThreadPoolExecutor) fixedThreadPool;
    assertEquals(POOL_SIZE, executor.getCorePoolSize());
    assertEquals(POOL_SIZE, executor.getMaximumPoolSize());
    /*
     * The largest number of threads that have every simultaneously
     * been in the pool. Since no threads has been submitted, the
     * largest pool size is 0.
     */
    assertEquals(0, executor.getLargestPoolSize());
    /*
     * Linked blocking queue is an optionally-bounded blocking queue
     * based on linked nodes.
     */
    assertEquals(LinkedBlockingQueue.class, executor.getQueue().getClass());
  }

  @Test
  public void cachedThreadPool() throws Exception {
    ThreadPoolExecutor executor = (ThreadPoolExecutor) cachedThreadPool;
    assertEquals(0, executor.getCorePoolSize());
    assertEquals(Integer.MAX_VALUE, executor.getMaximumPoolSize());
    /*
     * Synchronous queue is a blocking queue in which each insert
     * operation must wait for a corresponding remove operation by
     * another thread, and vice versa.
     *
     * A synchronous queue does not have any internal capacity, not
     * even a capacity of one.
     */
    assertEquals(SynchronousQueue.class, executor.getQueue().getClass());
  }

  @Test
  public void singleThreadPool() throws Exception {
    /*
     * Single thread pool is equivalent to `newFixedThreadPool(1)`.
     * However, this one is guaranteed to not being reconfigured
     * to use additional threads, by extending class
     * `DelegatedExecutorService`.
     *
     * `DelegatedExecutorService` is a wrapper class that exposes
     * only the `ExecutorService` methods of an `ExecutorService`
     * implementation.
     */
  }

  @Test
  public void scheduledThreadPool() throws Exception {
    Callable<Integer> task = () -> sum.incrementAndGet();
    ScheduledFuture<Integer> future = scheduledThreadPool.schedule(task, 1L, TimeUnit.MILLISECONDS);
    scheduledThreadPool.awaitTermination(1L, TimeUnit.SECONDS);

    assertTrue(future.isDone());
    assertEquals(1, future.get().intValue());
  }

  @Test
  public void workingStealingPool() throws Exception {
    assertEquals(ForkJoinPool.class, workingStealingPool.getClass());
  }
}
