package io.mincong.ocajp.chapter2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Review question of chapter 2 - Operators and Statements.
 *
 * @author Mincong Huang
 */
public class ReviewQuestionsTest {

  /**
   * Question 2: What data type (or types) will allow the following code snippet to compile? (Choose
   * all the apply)
   *
   * <pre>
   * byte x = 5;
   * byte y = 10;
   * ____ z = x + y;
   * </pre>
   *
   * <p>The value {@code x + y} is automatically promoted to {@code int}, so {@code int} and data
   * types that can be promoted automatically from {@code int} will work.
   */
  @Test
  public void testTypePromotionWithByte() {
    byte x = 5;
    byte y = 10;
    int myInt = x + y;
    long myLong = x + y;
    double myDouble = x + y;

    assertEquals(15, myInt);
    assertEquals(15L, myLong);
    assertEquals(15f, myDouble, 0.000_000_1);

    // TODO uncomment the following lines to see what happens
    // byte myByte = x + y; // incompatible type
    // short myShort = x + y; // incompatible type
    // boolean myBool = x + y; // incompatible type

    // going back to smaller data types need an explicit cast
    byte expectedByte = 15;
    short myByte = (byte) (x + y);
    short expectedShort = 15;
    short myShort = (short) (x + y);
    assertEquals(expectedByte, myByte);
    assertEquals(expectedShort, myShort);
  }

  /**
   * Question 9.
   *
   * <p>The expression inside the loop increments {@code i} but then assigns {@code i} to the old
   * values. Therefore, {@code i} ends the loop with the same value that it starts with: {@literal
   * 0}. The loop will repeat infinitely, outputting the same statement over and over again because
   * {@code i} remains {@literal 0} after every iteration of the loop.
   */
  @Test
  public void testInfiniteLoop() {
    int tries = 0;
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < 10; ) {
      i = i++;

      // debug starts
      builder.append(i);
      tries++;
      if (tries > 20) {
        break;
      }
      // debug ends
    }

    assertEquals("000000000000000000000", builder.toString());
  }

  /** Question 10. */
  @Test
  public void testTypePromotionWithSingleCast() {
    byte a = 40, b = 50;

    // byte sum = (byte) a + b;
    //                   ^
    // Only a is casted to byte, while b is promoted to int. Thus, the program does not compile.

    byte sum = (byte) (a + b);
    assertEquals(90, sum);
  }

  /**
   * Question 12. What is the output of the following code snippet?
   *
   * <pre>
   * int x = 0;
   * String s = null;
   * if (x == s) {
   *   System.out.println("Success");
   * } else {
   *   System.out.println("Failure");
   * }
   * </pre>
   *
   * <p>The variable {@code x} is an {@code int} and {@code s} is a reference to a {@code String}
   * object. The 2 data types are incomparable because neither variable can be converted to the
   * other variable's type.
   */
  @Test
  public void testTypeCompatibility() {
    // see comment
  }

  /**
   * Question 15. What is the output of the following code snippet?
   *
   * <pre>
   * int x = 1, y = 15;
   * while x < 10
   *   y--;
   *   x++;
   * System.out.println(x + ", " + y);
   * </pre>
   *
   * <p>The code does not compile because the {@code while} loop is missing parentheses.
   */
  @Test
  public void testWhileStatement() {
    // see comment
  }

  /**
   * Question 20.
   *
   * <p>The value of grade is 'B', but there's no {@code break} statement after the case, so the
   * next case statement will be reached, and 'C' will be printed.
   */
  @Test
  public void testSwitchStatement() {
    StringBuilder builder = new StringBuilder();
    final char a = 'A', d = 'D';
    char grade = 'B';
    switch (grade) {
      case a:
      case 'B':
        builder.append("B");
      case 'C':
        builder.append("C");
        break;
      case d:
      case 'F':
        builder.append("F");
    }
    assertEquals("BC", builder.toString());
  }
}
