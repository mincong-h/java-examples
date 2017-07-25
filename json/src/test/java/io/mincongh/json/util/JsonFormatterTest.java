package io.mincongh.json.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class JsonFormatterTest {

  @Test
  public void testPretty_jsonObject() throws Exception {
    String input = "{\"key1\":\"value1\",\"key2\":\"value2\"}";
    String actual = JsonFormatter.pretty(input);
    String expected = String.format(Locale.ROOT, ""
        + "{%n"
        + "  \"key1\": \"value1\",%n"
        + "  \"key2\": \"value2\"%n"
        + "}%n");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testPretty_jsonArray() throws Exception {
    String input = "["
        + "{\"key1\":\"value1\",\"key2\":\"value2\"},"
        + "{\"key1\":\"value1\",\"key2\":\"value2\"}]";
    String actual = JsonFormatter.pretty(input);
    String expected = String.format(Locale.ROOT, ""
        + "[%n"
        + "  {%n"
        + "    \"key1\": \"value1\",%n"
        + "    \"key2\": \"value2\"%n"
        + "  },%n"
        + "  {%n"
        + "    \"key1\": \"value1\",%n"
        + "    \"key2\": \"value2\"%n"
        + "  }%n"
        + "]%n");
    assertThat(actual).isEqualTo(expected);
  }

}
