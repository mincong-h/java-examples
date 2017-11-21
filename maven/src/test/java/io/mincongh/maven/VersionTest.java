package io.mincongh.maven;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class VersionTest {

  @Test(expected = IllegalArgumentException.class)
  public void constructor_majorNegative() throws Exception {
    Version.of(-1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_minorNegative() throws Exception {
    Version.of(0, -1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_patchNegative() throws Exception {
    Version.of(0, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_allZeros() throws Exception {
    Version.of(0, 0, 0);
  }

  @Test
  public void constructor_notAllZeros() throws Exception {
    Version.of(1, 0, 0);
    Version.of(0, 1, 0);
    Version.of(0, 0, 1);
  }

  @Test
  public void getMajor() throws Exception {
    assertThat(Version.of(1, 2, 3).getMajor()).isEqualTo(1);
  }

  @Test
  public void getMinor() throws Exception {
    assertThat(Version.of(1, 2, 3).getMinor()).isEqualTo(2);
  }

  @Test
  public void getPatch() throws Exception {
    assertThat(Version.of(1, 2, 3).getPatch()).isEqualTo(3);
  }

  @Test
  public void nextMajor() throws Exception {
    Version v = Version.of(1, 0, 0);
    assertThat(v.nextMajor()).isEqualTo(Version.of(2, 0, 0));
  }

  @Test
  public void nextMinor() throws Exception {
    Version v = Version.of(1, 0, 0);
    assertThat(v.nextMinor()).isEqualTo(Version.of(1, 1, 0));
  }

  @Test
  public void nextPatch() throws Exception {
    Version v = Version.of(1, 0, 0);
    assertThat(v.nextPatch()).isEqualTo(Version.of(1, 0, 1));
  }

  @Test
  public void compareMajor() throws Exception {
    Version v1 = Version.of(1, 0, 0);
    Version v2 = Version.of(2, 0, 0);
    assertThat(v2).isGreaterThan(v1);
  }

  @Test
  public void compareMinor() throws Exception {
    Version v1_0 = Version.of(1, 0, 0);
    Version v1_1 = Version.of(1, 1, 0);
    assertThat(v1_1).isGreaterThan(v1_0);
  }

  @Test
  public void comparePatch() throws Exception {
    Version v1_0_0 = Version.of(1, 0, 0);
    Version v1_0_1 = Version.of(1, 0, 1);
    assertThat(v1_0_1).isGreaterThan(v1_0_0);
  }

  @Test
  public void isAfter() throws Exception {
    Version v1 = Version.of(1, 0, 0);
    Arrays.asList(Version.of(1, 0, 1), Version.of(1, 1, 0), Version.of(2, 0, 0))
        .forEach(v -> assertTrue("Version " + v + " must be after " + v1, v.isAfter(v1)));
  }

  @Test
  public void isBefore() throws Exception {
    Version v1_1_1 = Version.of(1, 1, 1);
    Arrays.asList(Version.of(1, 0, 1), Version.of(1, 0, 1), Version.of(0, 1, 1))
        .forEach(v -> assertTrue("Version " + v + " must be before " + v1_1_1, v.isBefore(v1_1_1)));
  }

  @Test
  public void sameHashCode() throws Exception {
    Version a = Version.of(1, 0, 0);
    Version b = Version.of(1, 0, 0);
    assertEquals(a.hashCode(), b.hashCode());
    assertEquals(0, a.compareTo(b));
  }

  @Test
  public void equals_diffMajor() throws Exception {
    assertThat(Version.of(1, 1, 1)).isNotEqualTo(Version.of(0, 1, 1));
  }

  @Test
  public void equals_diffMinor() throws Exception {
    assertThat(Version.of(1, 1, 1)).isNotEqualTo(Version.of(1, 0, 1));
  }

  @Test
  public void equals_diffPatch() throws Exception {
    assertThat(Version.of(1, 1, 1)).isNotEqualTo(Version.of(1, 1, 0));
  }

  @Test
  public void equals_diffObject() throws Exception {
    assertThat(Version.of(1, 1, 1)).isNotEqualTo("1.1.1");
  }

}
