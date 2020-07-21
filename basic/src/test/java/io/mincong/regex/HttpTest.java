package io.mincong.regex;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

/**
 * Test different regular expressions related to HTTP / HTTPS.
 *
 * @author Mincong Huang
 */
class HttpTest {

  private static final List<String> COOKIE_EXPRESSIONS =
      Arrays.asList(
          "Set-Cookie: JSESSIONID=123; Path=/hello/; secure",
          "Set-Cookie: JSESSIONID=123; Path=/hello/; HttpOnly",
          "Set-Cookie: JSESSIONID=123; Path=/hello/; secure; HttpOnly",
          "Set-Cookie: JSESSIONID=123; Path=/hello/; HttpOnly; secure");

  private Pattern p;

  @Test
  void testSetCookieSecure() {
    p = Pattern.compile("^(.*)");
    COOKIE_EXPRESSIONS.forEach(s -> assertTrue(p.matcher(s).find()));

    p = Pattern.compile("(?i)^((?:(?!;\\s?secure).)+)");
    COOKIE_EXPRESSIONS.forEach(s -> assertTrue(p.matcher(s).find()));
  }

  @Test
  void testSetCookieHttpOnly() {
    p = Pattern.compile("^(.*)");
    COOKIE_EXPRESSIONS.forEach(s -> assertTrue(p.matcher(s).find()));

    p = Pattern.compile("(?i)^((?:(?!;\\s?HttpOnly).)+)");
    COOKIE_EXPRESSIONS.forEach(s -> assertTrue(p.matcher(s).find()));
  }
}
