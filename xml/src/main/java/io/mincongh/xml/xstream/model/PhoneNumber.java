package io.mincongh.xml.xstream.model;

import java.util.Locale;

/**
 * @author Mincong Huang
 */
public class PhoneNumber {

  private int code;

  private String number;

  public PhoneNumber(int code, String number) {
    this.code = code;
    this.number = number;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PhoneNumber)) {
      return false;
    }

    PhoneNumber that = (PhoneNumber) o;

    if (code != that.code) {
      return false;
    }
    return number != null ? number.equals(that.number) : that.number == null;
  }

  @Override
  public int hashCode() {
    int result = code;
    result = 31 * result + (number != null ? number.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return String.format(Locale.ENGLISH, "+%.2d %s", code, number);
  }

}
