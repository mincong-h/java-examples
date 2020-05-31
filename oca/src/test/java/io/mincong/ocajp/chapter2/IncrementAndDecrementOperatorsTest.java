package io.mincong.ocajp.chapter2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Increment and decrement operators, {@code ++} and {@code --}, respectively, can be applied to
 * numeric operands and have the higher order or precedence, as compared to binary operators. In
 * other words, they often get applied first to an expression.
 *
 * @author Mincong Huang
 */
public class IncrementAndDecrementOperatorsTest {

  @Test
  public void testIncrementAndDecrementSeparately() throws Exception {
    StringBuilder builder = new StringBuilder();
    int counter = 0;

    builder.append(counter);
    assertEquals("0", builder.toString());
    assertEquals(0, counter);

    builder.append(++counter);
    assertEquals("01", builder.toString());
    assertEquals(1, counter);

    builder.append(counter);
    assertEquals("011", builder.toString());
    assertEquals(1, counter);

    builder.append(counter--);
    assertEquals("0111", builder.toString());
    assertEquals(0, counter);

    builder.append(counter);
    assertEquals("01110", builder.toString());
    assertEquals(0, counter);
  }

  @Test
  public void testIncrementAndDecrementTogether() throws Exception {
    int x;

    x = 3;
    assertEquals(7, ++x * 5 / x-- + --x);
    assertEquals(2, x);

    x = 3;
    assertEquals(7, 4 * 5 / x-- + --x);
    assertEquals(7, 4 * 5 / 4 + 2);
  }
}
