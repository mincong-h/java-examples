package io.mincongh.classmethod;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.mincongh.abstraction.Dog;

/**
 * Test different string representations of class {@code java.lang.Class<T>}.
 *
 * @author Mincong Huang
 */
public class ClassMethodTest {

  @Test
  public void testMethods() {
    assertEquals("class io.mincongh.abstraction.Dog", Dog.class.toString());
    assertEquals("io.mincongh.abstraction.Dog", Dog.class.getName());
    assertEquals("Dog", Dog.class.getSimpleName());
  }
}
