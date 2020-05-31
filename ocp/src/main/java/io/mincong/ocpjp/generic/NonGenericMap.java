package io.mincong.ocpjp.generic;

/** @author Mincong Huang */
public class NonGenericMap implements MyMap<String, Integer> {

  @Override
  public void put(String key, Integer value) {
    // ...
  }

  @Override
  public Integer get(String key) {
    return null;
  }

  //  // Won't compile: it doesn't override the abstract method
  //  // `get(String)` in `MyMap` (the return type of `get(String)`
  //  // should be declared by `Integer`).
  //  @Override
  //  public String get(String key) {
  //    return null;
  //  }
}
