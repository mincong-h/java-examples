package io.mincong.ocajp.introduction;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * What happens if {@code foreach} has null input?
 */
public class ForeachNullTest {

  @Test
  public void testForeachNull() {
    String[] words = null;
    try {
      for (String word : words) {
        fail();
      }
    } catch (NullPointerException e) {
      // ok
    }
  }
}
