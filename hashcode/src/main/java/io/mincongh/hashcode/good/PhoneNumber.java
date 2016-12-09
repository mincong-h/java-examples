package io.mincongh.hashcode.good;

/**
 * @author Mincong Huang
 */
public class PhoneNumber {

  private final short areaCode;
  private final short prefix;
  private final short lineNumber;

  /**
   * Lazily initialized, cached hashCode. See Item 71 of
   * <li>Effective Java (Second Edition)</li>.
   */
  private volatile int hashCode;

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
   * <b>Do not be tempted to exclude significant parts of an object from the hash code computation
   * to improve performance.</b> While the resulting hash function may run faster, its poor quality
   * may degrade hash tables' performance to the point where they become unusably slow. In
   * particular, the hash function may, in practice, be confronted with a large collection of
   * instances that differ largely in the regions that you've chosen to ignore. If this happens, the
   * hash function will map all the instances to a very few hash codes, and hash-based collections
   * will display quadratic performance. This is not just a theoretical problem. The {@code String}
   * hash function implemented in all releases prior to 1.2 examined at most sixteen characters,
   * evenly spaced throughout the string, starting with the first character. For large collections
   * of hierarchical names, such as URLs, this hash function displayed exactly the pathological
   * behavior noted here.
   */
  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = 17;
      result = 31 * result + areaCode;
      result = 31 * result + prefix;
      result = 31 * result + lineNumber;
      hashCode = result;
    }
    return result;
  }
}
