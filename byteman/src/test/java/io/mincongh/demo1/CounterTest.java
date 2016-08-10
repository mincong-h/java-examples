package io.mincongh.demo1;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.jboss.byteman.contrib.bmunit.BMScript;
import org.jboss.byteman.contrib.bmunit.BMUnitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mincong Huang
 */
@RunWith(org.jboss.byteman.contrib.bmunit.BMUnitRunner.class)
@BMUnitConfig(loadDirectory = "target/test-classes")
@BMScript(value = "check.btm")
public class CounterTest {

    @Test
    public void textCounter() throws InterruptedException {
        Counter counter = new Counter(10);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(counter);
        int tries = 0;
        while (!future.isDone() && !future.isCancelled() && tries < 15) {
            System.out.println(tries + " - test is waiting the future to complete");
            TimeUnit.SECONDS.sleep(1);
            tries++;
        }
        String msgCancel = future.isCancelled() ? "cancelled" : "not cancelled";
        String msgDone = future.isDone() ? "done" : "not done";
        System.out.println(msgCancel);
        System.out.println(msgDone);
        try {
            int result = future.get();
            System.out.println("result is " + result);
            assertEquals(10, result);
        } catch (InterruptedException e) {
            System.err.println("Cannot get result, future is interrupted");
        } catch (ExecutionException e) {
            System.err.println("Cannot get result, future has execution pb");
        }
    }
}
