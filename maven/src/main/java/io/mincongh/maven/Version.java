package io.mincongh.maven;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mincong Huang
 */
public class Version implements Comparable<Version> {

  private final int major;

  private final int minor;

  private final int patch;

  /**
   * A version contains 3 groups of digits, separated by char '.'.
   * Each group of digits should be a positive integer, without
   * leading zeros.
   */
  private static final Pattern PATTERN =
      Pattern.compile("(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)");

  private Version(int major, int minor, int patch) {
    if (major < 0) {
      throw new IllegalArgumentException("Negative major version: " + major);
    }
    if (minor < 0) {
      throw new IllegalArgumentException("Negative minor version: " + minor);
    }
    if (patch < 0) {
      throw new IllegalArgumentException("Negative patch version: " + patch);
    }
    if (major == 0 && minor == 0 && patch == 0) {
      String msg = "At least one argument should be non-zero among major, minor, and patch.";
      throw new IllegalArgumentException(msg);
    }
    this.major = major;
    this.minor = minor;
    this.patch = patch;
  }

  public static Version of(int major, int minor, int patch) {
    return new Version(major, minor, patch);
  }

  public static Version parse(String version) {
    Matcher matcher = PATTERN.matcher(version);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Cannot parse version: " + version);
    }
    int major = Integer.parseInt(matcher.group(1));
    int minor = Integer.parseInt(matcher.group(2));
    int patch = Integer.parseInt(matcher.group(3));
    return new Version(major, minor, patch);
  }

  public Version nextMajor() {
    return new Version(major + 1, 0, 0);
  }

  public Version nextMinor() {
    return new Version(major, minor + 1, 0);
  }

  public Version nextPatch() {
    return new Version(major, minor, patch + 1);
  }

  public int getMajor() {
    return major;
  }

  public int getMinor() {
    return minor;
  }

  public int getPatch() {
    return patch;
  }

  public boolean isBefore(Version that) {
    return this.compareTo(that) < 0;
  }

  public boolean isAfter(Version that) {
    return this.compareTo(that) > 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Version)) {
      return false;
    }

    Version version = (Version) o;

    if (major != version.major) {
      return false;
    }
    if (minor != version.minor) {
      return false;
    }
    return patch == version.patch;
  }

  @Override
  public int hashCode() {
    int result = major;
    result = 31 * result + minor;
    result = 31 * result + patch;
    return result;
  }

  @Override
  public int compareTo(Version that) {
    if (this.major != that.major) {
      return this.major - that.major;
    }
    if (this.minor != that.minor) {
      return this.minor - that.minor;
    }
    if (this.patch != that.patch) {
      return this.patch - that.patch;
    }
    return 0;
  }

  @Override
  public String toString() {
    return major + "." + minor + "." + patch;
  }

}
