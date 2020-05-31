package io.mincong.ocpjp.concurrent;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

/** @author Mincong Huang */
public class ExecutorTest {

  @Test
  public void executeRunnable() throws Exception {
    List<Runnable> tasks = new ArrayList<>();
    AtomicInteger count = new AtomicInteger(0);
    for (int i = 0; i < 10; i++) {
      tasks.add(count::incrementAndGet);
    }

    Executor executor = Runnable::run;
    tasks.forEach(executor::execute);

    assertEquals(10, count.get());
  }
}
