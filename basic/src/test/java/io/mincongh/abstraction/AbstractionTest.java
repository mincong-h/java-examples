package io.mincongh.abstraction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Abstraction test.
 *
 * @author Mincong Huang
 */
class AbstractionTest {

  @Test
  void testAbstraction() {
    AbstractAnimal animal = new Dog();
    assertEquals("Abstract method implemented by Dog.", animal.abstractMethod());
    assertEquals("Implemented by Dog.", animal.implementedMethod());
    assertEquals("Final", animal.finalMethod());
  }

  @Test
  void testDog() {
    Dog dog = new Dog();
    assertEquals("Abstract method implemented by Dog.", dog.abstractMethod());
    assertEquals("Implemented by Dog.", dog.implementedMethod());
    assertEquals("Final", dog.finalMethod());
  }
}
