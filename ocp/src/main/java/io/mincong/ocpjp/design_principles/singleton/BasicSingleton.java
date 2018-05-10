package io.mincong.ocpjp.design_principles.singleton;

/**
 * Singleton is a creational design pattern that ensures that a class
 * is instantiated only once. The class also provides a global point
 * of access to it.
 * <p>
 * This pattern is useful when we require a single object of a class
 * to encapsulate all operations for managing a pool of resources,
 * and also to serve as a global point of reference.
 *
 * @author Mincong Huang
 */
public class BasicSingleton {

  private static BasicSingleton instance = null;

  // Concurrent access to of method `getInstance()` may result in
  // creation of multiple instances. This can be a problem in multi-
  // threaded environment, such as application servers and servlet
  // engines.
  public static BasicSingleton getInstance() {
    if (instance == null) {
      instance = new BasicSingleton();
    }
    return instance;
  }

  private BasicSingleton() {
    // Singleton, do not instantiate
  }

}
