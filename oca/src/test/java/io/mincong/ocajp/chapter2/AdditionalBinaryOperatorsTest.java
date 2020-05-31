package io.mincong.ocajp.chapter2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** @author Mincong Huang */
public class AdditionalBinaryOperatorsTest {

  @Test
  public void testShortMultiplication() {
    short x = 10;
    short y = 3;
    assertEquals(30, x * y);
    assertTrue(Integer.class.isInstance(x * y));
    assertFalse(Short.class.isInstance(x * y));
  }

  @Test
  public void testCastingPrimitiveValues() throws Exception {
    assertEquals(1, (int) 1.0);
    assertEquals(9, (int) 9f);

    // 1,921,222 = 0000 0000 0001 1101 0101 0000 1100 0110
    //           = ---- ---- ---- ---- 0101 0000 1100 0110
    //           = 20,678
    assertEquals(0b0000_0000_0001_1101_0101_0000_1100_0110, 1_921_222);
    assertEquals(0b0000_0000_0000_0000_0101_0000_1100_0110, (short) 1_921_222);
    assertEquals(20678, (short) 1_921_222);
  }

  @Test
  public void testCompoundAssignmentOperators() throws Exception {
    int x = 2;
    int z = 3;
    x *= z; // compound assignment chapter2
    assertEquals(6, x);

    long a = 10;
    int b = 5;
    //  b = b * a;  // does not compile
    b *= a;
    // The compound chapter2 will first cast `y` to a `long`, apply the multiplication of two `long`
    // values, and then cast the result to an `int`. Unlike the previous example, in which the
    // compiler threw an exception, in this example we see that the compiler will automatically cast
    // the resulting value to the data type of the value on the left-hand side of the compound
    // chapter2.
    assertTrue(Integer.class.isInstance(b));
    assertFalse(Long.class.isInstance(b));
  }

  @Test
  public void testEqualityOperators() throws Exception {
    assertTrue("left side should be promoted to a double", 5 == 5.0);
    boolean y = false;
    boolean x = (y = true);
    assertTrue(x);
  }
}
