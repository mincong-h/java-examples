package io.mincongh.hashcode.bad;

/**
 * @author Mincong Huang
 */
public class PhoneNumberNoHash {

  private final short areaCode;
  private final short prefix;
  private final short lineNumber;

  PhoneNumberNoHash(int areaCode, int prefix, int lineNumber) {
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
    if (!(o instanceof PhoneNumberNoHash))
      return false;
    PhoneNumberNoHash pn = (PhoneNumberNoHash) o;
    return pn.lineNumber == lineNumber && pn.prefix == prefix && pn.areaCode == areaCode;
  }

  // Broken - no hashCode method!
}
