package io.mincong.ocpjp.design_principles.singleton;

/**
 * Singleton with synchronized lazy initialization
 *
 * @author Mincong Huang
 */
public class SyncSingleton {

  // No eager initialization
  private static SyncSingleton instance;

  /**
   * Defining the method as <tt>synchronized</tt> means that multiple
   * threads or objects can't execute this method concurrently. So
   * this again saves us from multiple-object creation of a class
   * implementing the singleton pattern.
   * <p>
   * <tt>synchronized</tt> methods don't allow concurrent execution.
   * However, you application may feel a performance hit if a lof of
   * classes in your application call method #getInstance().
   *
   * @see SyncSingleton2#getInstance()
   */
  synchronized public static SyncSingleton getInstance() {
    if (instance == null) {
      instance = new SyncSingleton();
    }
    return instance;
  }

  private SyncSingleton() {
    // Singleton, do not instantiate
  }

}
