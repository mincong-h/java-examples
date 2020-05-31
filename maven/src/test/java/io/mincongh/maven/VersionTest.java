package io.mincongh.maven;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
public class VersionTest {

  @Test
  void constructor_majorNegative() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> Version.of(-1, 0, 0));
  }

  @Test
  void constructor_minorNegative() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> Version.of(0, -1, 0));
  }

  @Test
  void constructor_patchNegative() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> Version.of(0, 0, -1));
  }

  @Test
  void constructor_allZeros() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> Version.of(0, 0, 0));
  }

  @Test
  void constructor_notAllZeros() throws Exception {
    Version.of(1, 0, 0);
    Version.of(0, 1, 0);
    Version.of(0, 0, 1);
  }

  @Test
  void getMajor() throws Exception {
    assertThat(Version.of(1, 2, 3).getMajor()).isEqualTo(1);
  }

  @Test
  void getMinor() throws Exception {
    assertThat(Version.of(1, 2, 3).getMinor()).isEqualTo(2);
  }

  @Test
  void getPatch() throws Exception {
    assertThat(Version.of(1, 2, 3).getPatch()).isEqualTo(3);
  }

  @Test
  void nextMajor() throws Exception {
    assertThat(Version.of(1, 0, 0).nextMajor()).isEqualTo(Version.ofSnapshot(2, 0, 0));
    assertThat(Version.of(1, 1, 0).nextMajor()).isEqualTo(Version.ofSnapshot(2, 0, 0));
    assertThat(Version.of(1, 1, 1).nextMajor()).isEqualTo(Version.ofSnapshot(2, 0, 0));
  }

  @Test
  void nextMinor() throws Exception {
    assertThat(Version.of(1, 0, 0).nextMinor()).isEqualTo(Version.ofSnapshot(1, 1, 0));
    assertThat(Version.of(1, 1, 0).nextMinor()).isEqualTo(Version.ofSnapshot(1, 2, 0));
    assertThat(Version.of(1, 1, 1).nextMinor()).isEqualTo(Version.ofSnapshot(1, 2, 0));
  }

  @Test
  void nextPatch() throws Exception {
    assertThat(Version.of(1, 0, 0).nextPatch()).isEqualTo(Version.ofSnapshot(1, 0, 1));
    assertThat(Version.of(1, 1, 0).nextPatch()).isEqualTo(Version.ofSnapshot(1, 1, 1));
    assertThat(Version.of(1, 1, 1).nextPatch()).isEqualTo(Version.ofSnapshot(1, 1, 2));
  }

  @Test
  void compareMajor() throws Exception {
    Version v1 = Version.of(1, 0, 0);
    Version v2 = Version.of(2, 0, 0);
    assertThat(v2).isGreaterThan(v1);

    Version s1 = Version.ofSnapshot(1, 0, 0);
    Version s2 = Version.ofSnapshot(2, 0, 0);
    assertThat(s2).isGreaterThan(s1);
  }

  @Test
  void compareMinor() throws Exception {
    Version v1_0 = Version.of(1, 0, 0);
    Version v1_1 = Version.of(1, 1, 0);
    assertThat(v1_1).isGreaterThan(v1_0);

    Version s1_0 = Version.ofSnapshot(1, 0, 0);
    Version s1_1 = Version.ofSnapshot(1, 1, 0);
    assertThat(s1_1).isGreaterThan(s1_0);
  }

  @Test
  void comparePatch() throws Exception {
    Version v1_0_0 = Version.of(1, 0, 0);
    Version v1_0_1 = Version.of(1, 0, 1);
    assertThat(v1_0_1).isGreaterThan(v1_0_0);

    Version s1_0_0 = Version.ofSnapshot(1, 0, 0);
    Version s1_0_1 = Version.ofSnapshot(1, 0, 1);
    assertThat(s1_0_1).isGreaterThan(s1_0_0);
  }

  @Test
  void compareSnapshot() throws Exception {
    Version release1 = Version.of(1, 0, 0);

    assertThat(Version.ofSnapshot(1, 0, 0)).isLessThan(release1);
    assertThat(Version.ofSnapshot(2, 0, 0)).isGreaterThan(release1);
    assertThat(Version.ofSnapshot(1, 1, 0)).isGreaterThan(release1);
    assertThat(Version.ofSnapshot(1, 0, 1)).isGreaterThan(release1);
  }

  @Test
  void isAfter() throws Exception {
    Version v1 = Version.of(1, 0, 0);
    Arrays.asList(Version.of(1, 0, 1), Version.of(1, 1, 0), Version.of(2, 0, 0))
        .forEach(v -> assertTrue(v.isAfter(v1), "Version " + v + " must be after " + v1));
  }

  @Test
  void isBefore() throws Exception {
    Version v1_1_1 = Version.of(1, 1, 1);
    Arrays.asList(Version.of(1, 0, 1), Version.of(1, 0, 1), Version.of(0, 1, 1))
        .forEach(v -> assertTrue(v.isBefore(v1_1_1), "Version " + v + " must be before " + v1_1_1));
  }

  @Test
  void sameHashCode() throws Exception {
    Version a = Version.of(1, 0, 0);
    Version b = Version.of(1, 0, 0);
    assertEquals(a.hashCode(), b.hashCode());
    assertEquals(0, a.compareTo(b));
  }

  @Test
  void equals_diffMajor() throws Exception {
    assertThat(Version.of(1, 1, 1)).isNotEqualTo(Version.of(0, 1, 1));
  }

  @Test
  void equals_diffMinor() throws Exception {
    assertThat(Version.of(1, 1, 1)).isNotEqualTo(Version.of(1, 0, 1));
  }

  @Test
  void equals_diffPatch() throws Exception {
    assertThat(Version.of(1, 1, 1)).isNotEqualTo(Version.of(1, 1, 0));
  }

  @Test
  void equals_diffObject() throws Exception {
    assertThat(Version.of(1, 1, 1)).isNotEqualTo("1.1.1");
  }

  @Test
  void parse_snapshotOk() throws Exception {
    assertThat(Version.parse("1.0.0-SNAPSHOT")).isEqualTo(Version.ofSnapshot(1, 0, 0));
    assertThat(Version.parse("0.1.0-SNAPSHOT")).isEqualTo(Version.ofSnapshot(0, 1, 0));
    assertThat(Version.parse("0.0.1-SNAPSHOT")).isEqualTo(Version.ofSnapshot(0, 0, 1));

    assertTrue(Version.parse("1.0.0-SNAPSHOT").isSnapshot());
    assertTrue(Version.parse("0.1.0-SNAPSHOT").isSnapshot());
    assertTrue(Version.parse("0.0.1-SNAPSHOT").isSnapshot());
  }

  @Test
  void parse_snapshotWrongSpelled() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> Version.parse("1.0.0-SNAPSHAT"));
  }

  @Test
  void parse_releaseOk() throws Exception {
    assertThat(Version.parse("1.0.0")).isEqualTo(Version.of(1, 0, 0));
    assertThat(Version.parse("0.1.0")).isEqualTo(Version.of(0, 1, 0));
    assertThat(Version.parse("0.0.1")).isEqualTo(Version.of(0, 0, 1));
  }

  @Test
  void parse_leadingZeroAtMajor() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> Version.parse("01.0.0"));
  }

  @Test
  void parse_leadingZeroAtMinor() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> Version.parse("0.01.0"));
  }

  @Test
  void parse_leadingZeroAtPatch() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> Version.parse("0.0.01"));
  }

  @Test
  void toString_release() throws Exception {
    assertEquals("1.2.3", Version.of(1, 2, 3).toString());
  }

  @Test
  void toString_snapshot() throws Exception {
    assertEquals("1.2.3-SNAPSHOT", Version.ofSnapshot(1, 2, 3).toString());
  }
}
