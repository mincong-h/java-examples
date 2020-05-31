package io.mincong.ocajp.introduction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Given the code fragment:
 *
 * <pre>
 * float x = 22.00f % 3.00f;
 * int y = 22 % 3;
 * System.out.print(x + ", " + y);
 * </pre>
 *
 * What is the result?
 *
 * @author Mincong HUANG
 */
public class ModuloTest {

  @Test
  public void testMod() {
    float x = 22.00f % 3.00f;
    int y = 22 % 3;
    assertEquals(1, x, 0.000000001);
    assertEquals(1, y);
  }
}
