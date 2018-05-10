package io.mincong.ocpjp.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class ExecutorServiceTest {

  private static final int COUNT = 3;

  private ExecutorService threadPool;

  private AtomicInteger sum;

  private List<Callable<Integer>> tasks;

  @Before
  public void setUp() throws Exception {
    threadPool = Executors.newFixedThreadPool(2);

    sum = new AtomicInteger(0);
    tasks = new ArrayList<>(COUNT);
    for (int i = 0; i < COUNT; i++) {
      tasks.add(() -> sum.incrementAndGet());
    }
  }

  @After
  public void tearDown() throws Exception {
    threadPool.shutdownNow();
  }

  /* java.lang.Runnable */

  @Test
  public void submit_runnable() throws Exception {
    threadPool.submit(new FakeRunnable(sum));
    threadPool.submit(new FakeRunnable(sum));
    threadPool.awaitTermination(1, TimeUnit.SECONDS);

    assertEquals(20, sum.get());
  }

  @Test(expected = Exception.class)
  public void submit_runnableLambda() throws Exception {
    Future<?> future = threadPool.submit(() -> {
      throw new Exception("Something goes wrong.");
    });
    future.get(1, TimeUnit.SECONDS);
  }

  private static class FakeRunnable implements Runnable {

    private AtomicInteger i;

    private FakeRunnable(AtomicInteger i) {
      this.i = i;
    }

    @Override
    public void run() {
      i.addAndGet(10);
    }
  }

  /* java.util.concurrent.Callable */

  @Test(expected = Exception.class)
  public void submit_callable() throws Exception {
    Future<Integer> future = threadPool.submit(new FakeCallable());
    future.get(1, TimeUnit.SECONDS);
  }

  private static class FakeCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
      throw new Exception("Cannot return anything.");
    }
  }

  @Test
  public void submit_callableVoid() throws Exception {
    Callable<Void> task = () -> {
      sum.set(10);
      return null;
    };
    Future<Void> future = threadPool.submit(task);
    future.get(1, TimeUnit.SECONDS);
    assertEquals(10, sum.get());
  }

  @Test
  public void submit_callableLambda() throws Exception {
    threadPool.submit(() -> sum.addAndGet(10));
    threadPool.submit(() -> sum.addAndGet(20));
    threadPool.awaitTermination(1, TimeUnit.SECONDS);

    assertEquals(30, sum.get());
  }
  @Test
  public void invokeAll() throws Exception {
    List<Future<Integer>> futures = threadPool.invokeAll(tasks);
    futures.forEach(f -> assertTrue(f.isDone()));
    assertEquals(COUNT, sum.get());
  }

  @Test
  public void invokeAny() throws Exception {
    tasks.add(() -> {
      throw new Exception("Callable fails on purpose.");
    });
    // The result is returned by one of the tasks.
    int result = threadPool.invokeAny(tasks);
    assertTrue(result > 0);
  }


}
