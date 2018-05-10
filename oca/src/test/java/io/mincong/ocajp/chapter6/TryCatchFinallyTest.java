package io.mincong.ocajp.chapter6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * A statement is required to have a {@code catch} and / or {@code finally} clause. If it goes the
 * {@code catch} route, it is allowed to have multiple {@code catch} clauses.
 *
 * @author Mincong Huang
 */
public class TryCatchFinallyTest {

  @Test
  public void testTryCatchCombination() {
    try {
      Integer.parseInt("A");
      fail();
    } catch (NumberFormatException e) {
      assertEquals("For input string: \"A\"", e.getMessage());
    }
  }

  @Test(expected = NumberFormatException.class)
  public void testTryFinallyCombination() {
    try {
      Integer.parseInt("A");
    } finally {
      // do nothing
    }
  }

}
