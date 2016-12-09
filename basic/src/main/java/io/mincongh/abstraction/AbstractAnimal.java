package io.mincongh.abstraction;

/**
 * An abstract class is a class which cannot be instantiated. An abstract class is used by creating
 * an inheriting subclass that can be instantiated. An abstract class does a few things for the
 * inheriting subclass:
 * <ul>
 * <li>Define methods which can be used by the inheriting subclass.
 * <li>Define abstract methods which the inheriting subclass must implement.
 * <li>Provide a common interface which allows the subclass to be interchanged with all other
 * subclasses.
 * </ul>
 * From {@link http://stackoverflow.com/questions/1320745/abstract-class-in-java}.
 *
 * @author Mincong Huang
 */
public abstract class AbstractAnimal {

  AbstractAnimal() {}

  /**
   * Abstract method do not have body.
   */
  public abstract String abstractMethod();

  public String implementedMethod() {
    return "Implemented";
  }

  /**
   * This implementation is final. No other implementations will be allowed.
   */
  public final String finalMethod() {
    return "Final";
  }
}
