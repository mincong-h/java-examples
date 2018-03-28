package io.mincongh.regex;

import java.util.regex.Pattern;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RegexTest {

  @Test
  public void packageName() {
    String[] pkgNames = {"foo-9.10.json", "bar-10.1.json"};
    Pattern pattern = Pattern.compile("(foo|bar)-(\\d+\\.\\d+)\\.json");
    for (String name : pkgNames) {
      assertTrue(pattern.matcher(name).matches());
    }
  }

  @Test
  public void widgetTypeName() {
    String[] widgetNames = {"foo-widget-1.0.json", "bar-widget-2.1.json"};
    Pattern pattern = Pattern.compile("(\\w+)-widget-(\\d+\\.\\d+)\\.json");
    for (String name : widgetNames) {
      assertTrue(pattern.matcher(name).matches());
    }
  }

  /** Regex pattern does not accept null value. */
  @Test(expected = NullPointerException.class)
  public void nullValue() {
    Pattern pattern = Pattern.compile("\\d+");
    pattern.matcher(null);
  }
}
