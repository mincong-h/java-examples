package io.mincong.ocpjp.design_principles.singleton;

/** @author Mincong Huang */
public class EagerInitSingleton {

  // It executes when the class is loaded by the Java class loaders.
  // So  an object of class EagerInitSingleton is created before any
  // class requests it.
  // The problem is, eager initialization creates an object of class
  // `EagerInitSingleton` even if it's never used.
  private static final EagerInitSingleton instance = new EagerInitSingleton();

  public static EagerInitSingleton getInstance() {
    return instance;
  }
}
