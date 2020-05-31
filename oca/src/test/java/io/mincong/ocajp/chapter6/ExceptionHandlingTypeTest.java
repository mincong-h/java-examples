package io.mincong.ocajp.chapter6;

import org.junit.Test;

/** @author Mincong Huang */
public class ExceptionHandlingTypeTest {

  /**
   * Errors extend the {@link java.lang.Error} class. They are thrown by the JVM and should not be
   * handled or declared. Legally, you can handle {@link java.lang.Error} subclasses, but it's not a
   * good idea.
   */
  @Test
  public void testHandleError() {
    try {
      throw new StackOverflowError();
    } catch (StackOverflowError e) {
      // ok
    }
  }

  @Test
  public void testHandleCheckedException() {
    try {
      throw new Exception();
    } catch (Exception e) {
      // ok
    }
  }

  @Test
  public void testHandleRuntimeException() {
    try {
      throw new RuntimeException();
    } catch (RuntimeException e) {
      // ok
    }
  }

  @Test(expected = RuntimeException.class)
  public void testDeclareRuntimeExceptionIsUnnecessary() {
    throw new RuntimeException();
  }

  @Test(expected = Exception.class)
  public void testDeclareCheckedExceptionIsMandatory() throws Exception {
    throw new Exception();
  }
}
