package io.mincong.ocpjp.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class AtomicIntegerTest {

  private AtomicInteger i;

  @Before
  public void setUp() throws Exception {
    i = new AtomicInteger(0);
  }

  @Test
  public void addAndGet() throws Exception {
    assertEquals(2, i.addAndGet(2));
  }

  @Test
  public void getAndAdd() throws Exception {
    assertEquals(0, i.getAndAdd(2));
    assertEquals(2, i.get());
  }

  @Test
  public void compareAndSet() throws Exception {
    // expected value not matched, i not updated
    assertFalse(i.compareAndSet(1, 2));
    assertEquals(0, i.get());

    // expected value matched, i updated
    assertTrue(i.compareAndSet(0, 2));
    assertEquals(2, i.get());
  }

  @Test
  public void getAndSet() throws Exception {
    assertEquals(0, i.getAndSet(-1));
    assertEquals(-1, i.get());
  }

  @Test
  public void set() throws Exception {
    i.set(2);
    assertEquals(2, i.get());
  }

  @Test
  public void getAndDecrement() throws Exception {
    assertEquals(0, i.getAndDecrement());
    assertEquals(-1, i.get());
  }

  @Test
  public void getAndIncrement() throws Exception {
    assertEquals(0, i.getAndIncrement());
    assertEquals(1, i.get());
  }

  @Test
  public void decrementAndGet() throws Exception {
    assertEquals(-1, i.decrementAndGet());
  }

  @Test
  public void incrementAndGet() throws Exception {
    assertEquals(1, i.incrementAndGet());
  }

}
