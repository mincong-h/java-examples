package io.mincong.ocajp.chapter2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Numeric Promotion Rules:
 * <ul>
 * <li>If two values have different data types, Java will automatically promote one of the values to
 * the larger of the two data types.
 * <li>If one of the values is integral and the other is floating-point, Java will automatically
 * promote the integral value to hte floating-point value's data type.
 * <li>Smaller data types, namely {@code byte}, {@code short}, and {@code char}, are first promoted
 * to {@code int} any time they're used with a Java arithmetic chapter2, even if neither of the
 * operands is {@code int}.
 * <li>After all promotion has occurred and the operands have the same data type, the resulting
 * value will have the same data type as its promoted operands.
 * </ul>
 *
 * @author Mincong Huang
 */
public class NumericPromotionTest {

  @Test
  public void testPromotionToLong() throws Exception {
    int x = 1;
    long y = 33;
    assertTrue(Long.class.isInstance(x + y));
    assertFalse(Integer.class.isInstance(x + y));
  }

  @Test
  public void testPromotionToDouble() throws Exception {
    double x = 39.21;
    float y = 2.1f;
    assertTrue(Double.class.isInstance(x + y));
    assertFalse(Float.class.isInstance(x + y));

    short a = 14;
    float b = 13;
    double c = 30;
    assertTrue(Float.class.isInstance(a * b));
    assertTrue(Double.class.isInstance(a * b / c));
    assertFalse(Float.class.isInstance(a * b / c));
  }

  @Test
  public void testPromotionToInt() throws Exception {
    short x = 10;
    short y = 3;
    assertTrue(Integer.class.isInstance(x / y));
    assertFalse(Short.class.isInstance(x / y));
    assertFalse(Long.class.isInstance(x / y));
    assertFalse(Float.class.isInstance(x / y));
    assertFalse(Double.class.isInstance(x / y));
  }

}
