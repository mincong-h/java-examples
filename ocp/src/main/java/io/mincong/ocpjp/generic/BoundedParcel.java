package io.mincong.ocpjp.generic;

/**
 * By specifying the bounds, we can restrict the set of types that
 * can be used as type arguments to a generic class, interface, or
 * method. It also enables access to the methods (and variables)
 * defined by the bounds.
 *
 * @author Mincong Huang
 */
public class BoundedParcel<T extends Gift, Exchangeable> {

  private T gift;

  public void set(T gift) {
    this.gift = gift;
  }

  public String getShipInfo() {
    if (gift.getWeight() > 3) {
      return "Ship by courier ABC";
    } else {
      return "Ship by courier XYZ";
    }
  }

}
