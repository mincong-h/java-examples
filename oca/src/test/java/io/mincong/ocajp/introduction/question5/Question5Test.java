package io.mincong.ocajp.introduction.question5;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Question 5: What is the output of the following code snippet?
 *
 * @author Mincong HUANG
 */
public class Question5Test {

  /**
   * In this test, all the lines of {@code System.out.print(...)} have been changed to {@code
   * StringBuilder.append(...)}.
   * <p>
   * {@code RuntimeException} is the superclass of those exceptions that can be thrown during the
   * normal operation of the Java Virtual Machine.
   * <p>
   * {@code RuntimeException} and its subclasses are <em>unchecked exceptions</em>. Unchecked
   * exceptions do <em>not</em> need to be declared in a method or constructor's {@code throws}
   * clause if they can be thrown by the execution of the method or constructor and propagate
   * outside the method or constructor boundary.
   */
  @Test
  public void testQuestion5() {
    StringBuilder builder = new StringBuilder();
    builder.append("a");
    try {
      builder.append("b");
      throw new IllegalArgumentException(); // extends RuntimeException
    } catch (RuntimeException e) {          // caught the exception thrown
      builder.append("c");
    } finally {
      builder.append("d");
    }
    builder.append("e");

    assertEquals("abcde", builder.toString());
  }

}
