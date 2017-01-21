package io.mincongh.number;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class NumberTest {

  @Test
  public void testUnderscore() {
    assertEquals(10, 1_0);
    assertEquals(1000, 1_000);
    assertEquals(1000000, 1_000_000);
    assertEquals(0.000001, 0.000_001, 0.0000000000001);
  }
}
