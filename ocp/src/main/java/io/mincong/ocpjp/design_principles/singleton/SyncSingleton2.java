package io.mincong.ocpjp.design_principles.singleton;

/**
 * Singleton with synchronized lazy initialization
 *
 * @author Mincong Huang
 */
public class SyncSingleton2 {

  // No eager initialization
  private static SyncSingleton2 instance;

  /**
   * By synchronizing method <tt>getInstance()</tt> partially, we can avoid some performance issue.
   *
   * @see SyncSingleton#getInstance()
   */
  public static SyncSingleton2 getInstance() {
    if (instance == null) {
      // Synchronize code block that creates new object
      // (the thread acquires a lock on this class)
      synchronized (SyncSingleton2.class) {
        if (instance == null) {
          instance = new SyncSingleton2();
        }
      }
    }
    return instance;
  }

  private SyncSingleton2() {
    // Singleton, do not instantiate
  }
}
