package io.mincong.ocajp.chapter3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.time.LocalDate;
import java.time.Month;
import org.junit.Test;

/** @author Mincong Huang */
public class ReviewQuestionTest {

  /**
   * Question 10: What is the result of the following code? (Choose all that apply)
   *
   * <pre>
   * 13: String a = ";
   * 14: a += 2;
   * 15: a += 'c';
   * 16: a += false;
   * 17: if (a == "2cfalse") System.out.println("==");
   * 18: if (a.equals("2cfalse")) System.out.println("equals");
   * </pre>
   *
   * <ul>
   *   <li>A. Compile error on line 14.
   *   <li>B. Compile error on line 15.
   *   <li>C. Compile error on line 16.
   *   <li>D. Compile error on another line.
   *   <li>E. ==
   *   <li>F. equals
   *   <li>G. An exception is thrown.
   * </ul>
   */
  @Test
  public void testString() {
    String a = "";
    a += 2;
    a += 'c';
    a += false;
    // Option E is wrong: 2 string objects are not the same in memory.
    // One comes directly from the string pool,
    // and the other comes from building using string operations
    assertNotSame("2cfalse", a);
    assertEquals("2cfalse", a);
  }

  /** Question 15. */
  @Test
  public void testLegalArrayDeclaration() {
    Object[][][] cubbies = new Object[3][0][5];
    // legal to leave out the size for later dimensions
    int[][] scores = new int[2][];
    java.util.Date[] dates[] = new java.util.Date[2][];
  }

  /** Question 31. */
  @Test
  @SuppressWarnings("ReturnValueIgnored")
  public void testLocalDateImmutability() {
    LocalDate date = LocalDate.of(2018, Month.APRIL, 30);
    date.plusDays(2);
    // local date is immutable
    assertEquals(30, date.getDayOfMonth());
  }
}
