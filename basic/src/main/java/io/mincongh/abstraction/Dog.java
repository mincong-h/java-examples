package io.mincongh.abstraction;

/**
 *
 * @author Mincong Huang
 */
public class Dog extends AbstractAnimal {

  @Override
  public String abstractMethod() {
    return "Abstract method implemented by Dog.";
  }

  @Override
  public String implementedMethod() {
    return "Implemented by Dog.";
  }
}
