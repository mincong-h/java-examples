package io.mincong.regex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class RegexTest {

  @Test
  void packageName() {
    String[] pkgNames = {"foo-9.10.json", "bar-10.1.json"};
    Pattern pattern = Pattern.compile("(foo|bar)-(\\d+\\.\\d+)\\.json");
    for (String name : pkgNames) {
      assertTrue(pattern.matcher(name).matches());
    }
  }

  @Test
  void widgetTypeName() {
    String[] widgetNames = {"foo-widget-1.0.json", "bar-widget-2.1.json"};
    Pattern pattern = Pattern.compile("(\\w+)-widget-(\\d+\\.\\d+)\\.json");
    for (String name : widgetNames) {
      assertTrue(pattern.matcher(name).matches());
    }
  }

  /** Regex pattern does not accept null value. */
  @Test
  void nullValue() {
    Pattern pattern = Pattern.compile("\\d+");
    assertThatNullPointerException().isThrownBy(() -> pattern.matcher(null));
  }

  @Test
  // Replacing a specific string in javReplacing a specific string in java
  void so50196946() {
    assertThat("hellol lol".replaceAll("\\blol\\b", "laugh out loud"))
        .isEqualTo("hellol laugh out loud");
  }

  @Test
  void namedGroup() {
    var pattern = Pattern.compile("^(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})$");
    var matcher = pattern.matcher("2020-07-21");
    assertThat(matcher.matches()).isTrue();
    assertThat(matcher.group("year")).isEqualTo("2020");
    assertThat(matcher.group("month")).isEqualTo("07");
    assertThat(matcher.group("day")).isEqualTo("21");
  }
}
