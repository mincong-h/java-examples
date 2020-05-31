package io.mincong.ocpjp.generic;

/** @author Mincong Huang */
// The type T must be kept as argument, so that it can be passed to
// the parameter of its base class. In lack of the type T, this class
// won't compile.
public class GenericBookParcel<X, T> extends Parcel<T> {

  private X x;

  public void setX(X x) {
    this.x = x;
  }

  public X getX() {
    return x;
  }
}
