package io.mincongh.abstraction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Abstraction test.
 *
 * @author Mincong Huang
 */
public class AbstractionTest {

  @Test
  public void testAbstraction() {
    AbstractAnimal animal = new Dog();
    assertEquals("Abstract method implemented by Dog.", animal.abstractMethod());
    assertEquals("Implemented by Dog.", animal.implementedMethod());
    assertEquals("Final", animal.finalMethod());
  }

  @Test
  public void testDog() {
    Dog dog = new Dog();
    assertEquals("Abstract method implemented by Dog.", dog.abstractMethod());
    assertEquals("Implemented by Dog.", dog.implementedMethod());
    assertEquals("Final", dog.finalMethod());
  }
}
