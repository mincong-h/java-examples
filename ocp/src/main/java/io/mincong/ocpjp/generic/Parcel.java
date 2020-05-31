package io.mincong.ocpjp.generic;

/** @author Mincong Huang */
public class Parcel<T> {

  private T t;

  public void setT(T t) {
    this.t = t;
  }

  public T getT() {
    return t;
  }
}
