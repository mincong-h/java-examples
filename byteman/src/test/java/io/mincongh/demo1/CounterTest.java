package io.mincongh.demo1;

import static org.junit.Assert.fail;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.jboss.byteman.contrib.bmunit.BMScript;
import org.jboss.byteman.contrib.bmunit.BMUnitConfig;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

/**
 * @author Mincong Huang
 */
@RunWith(org.jboss.byteman.contrib.bmunit.BMUnitRunner.class)
@BMUnitConfig(loadDirectory = "target/test-classes")
@BMScript(value = "check.btm")
@Ignore("Failed since JDK 9. BMUnit : Unable to identify test JVM process during agent load")
public class CounterTest {

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Test
  public void textCounter() throws ExecutionException, InterruptedException {

    expectedEx.expect(ExecutionException.class);
    expectedEx.expectMessage("Byteman : interrupt the program...");

    Counter counter = new Counter(10);
    ExecutorService executor = Executors.newFixedThreadPool(1);
    Future<Integer> future = executor.submit(counter);
    int tries = 0;

    // This method is waiting the future to complete
    while (!future.isDone() && !future.isCancelled() && tries < 15) {
      TimeUnit.SECONDS.sleep(1);
      tries++;
    }
    int end = future.get();
    fail("The future should be interrupted by byteman, but end=" + end);
  }
}
