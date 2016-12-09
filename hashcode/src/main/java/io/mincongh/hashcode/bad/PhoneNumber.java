package io.mincongh.hashcode.bad;

/**
 * @author Mincong Huang
 */
public class PhoneNumber {

  private final short areaCode;
  private final short prefix;
  private final short lineNumber;

  public PhoneNumber(int areaCode, int prefix, int lineNumber) {
    rangeCheck(areaCode, 999, "area code");
    rangeCheck(prefix, 999, "prefix");
    rangeCheck(lineNumber, 9999, "line number");
    this.areaCode = (short) areaCode;
    this.prefix = (short) prefix;
    this.lineNumber = (short) lineNumber;
  }

  private static void rangeCheck(int arg, int max, String name) {
    if (arg < 0 || arg > max) {
      throw new IllegalArgumentException(name + ": " + arg);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof PhoneNumber))
      return false;
    PhoneNumber pn = (PhoneNumber) o;
    return pn.lineNumber == lineNumber && pn.prefix == prefix && pn.areaCode == areaCode;
  }

  /**
   * The worst possible legal hash function - never use!
   * <p>
   * It's legal because it ensures that equal objects have the same hash code. It's atrocious
   * because it ensures that every object has the same hash code. Therefore, every object hashes to
   * the same bucket, and hash tables degenerate to linked lists. Programs that should run in linear
   * time instead run in quadratic time. For large hash tables, this is the difference between
   * working and not working.
   */
  @Override
  public int hashCode() {
    return 42;
  }
}
