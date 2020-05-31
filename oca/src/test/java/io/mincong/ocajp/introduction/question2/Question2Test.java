package io.mincong.ocajp.introduction.question2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/** @author Mincong HUANG */
public class Question2Test {

  /** Question 2: What it the result of the following code? */
  @Test
  public void testQuestion2() {
    String s1 = "Java";
    String s2 = "Java";
    StringBuilder sb1 = new StringBuilder();
    sb1.append("Ja").append("va");

    // String liberals are used from the string pool.
    assertSame("s1 and s2 should refer to the same object.", s1, s2);
    assertEquals("s1 and s2 should be equal.", s1, s2);
    assertNotSame(sb1.toString(), s1);
    assertEquals(sb1.toString(), s1);
  }
}
