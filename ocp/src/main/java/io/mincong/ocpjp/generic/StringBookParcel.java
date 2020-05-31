package io.mincong.ocpjp.generic;

/** @author Mincong Huang */
// The type T is no longer needed in this class. The derived class,
// StringBookParcel, passes type argument `String` to its generic
// base class in its declaration.
//
// So, a type argument must be passed to the type parameter of a base
// class. You can do so while extending the base class(*) or while
// instantiating the derived class.
//
// (*): In this case, we can extend a generic base class to define a
//      non-generic base class.
public class StringBookParcel<X> extends Parcel<String> {

  private X x;

  public void setX(X x) {
    this.x = x;
  }

  public X getX() {
    return x;
  }
}
