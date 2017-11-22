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

  private final boolean isSnapshot;

  /**
   * A version contains 3 groups of digits, separated by char '.'
   * (dot). Each group of digits should be a positive integer,
   * without leading zeros. If this is a snapshot version, its value
   * must end with the optional qualifier "-SNAPSHOT".
   */
  private static final Pattern PATTERN =
      Pattern.compile("(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(-SNAPSHOT)?");

  private Version(int major, int minor, int patch, boolean isSnapshot) {
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
    this.isSnapshot = isSnapshot;
  }

  public static Version of(int major, int minor, int patch) {
    return new Version(major, minor, patch, false);
  }

  public static Version ofSnapshot(int major, int minor, int patch) {
    return new Version(major, minor, patch, true);
  }

  public static Version parse(String version) {
    Matcher matcher = PATTERN.matcher(version);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Cannot parse version: " + version);
    }
    int major = Integer.parseInt(matcher.group(1));
    int minor = Integer.parseInt(matcher.group(2));
    int patch = Integer.parseInt(matcher.group(3));
    boolean isSnapshot = matcher.group(4) != null;

    return new Version(major, minor, patch, isSnapshot);
  }

  public Version nextMajor() {
    return new Version(major + 1, 0, 0, true);
  }

  public Version nextMinor() {
    return new Version(major, minor + 1, 0, true);
  }

  public Version nextPatch() {
    return new Version(major, minor, patch + 1, true);
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

  /**
   * Determines if this version is snapshot. A version is snapshot if
   * and only if its string representation ends with {@literal
   * -SNAPSHOT}.
   *
   * @return true if snapshot version
   */
  public boolean isSnapshot() {
    return isSnapshot;
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
    if (patch != version.patch) {
      return false;
    }
    return isSnapshot == version.isSnapshot;
  }

  @Override
  public int hashCode() {
    int result = major;
    result = 31 * result + minor;
    result = 31 * result + patch;
    result = 31 * result + (isSnapshot ? 1 : 0);
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
    if (this.isSnapshot != that.isSnapshot) {
      // Snapshot version is earlier than final version
      return this.isSnapshot ? -1 : 1;
    }
    return 0;
  }

  @Override
  public String toString() {
    if (isSnapshot) {
      return major + "." + minor + "." + patch + "-SNAPSHOT";
    } else {
      return major + "." + minor + "." + patch;
    }
  }

}
