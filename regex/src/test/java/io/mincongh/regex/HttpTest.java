package io.mincongh.regex;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.Test;

/**
 * Test different regular expressions related to HTTP / HTTPS.
 *
 * @author Mincong Huang
 */
public class HttpTest {

  private static final List<String> COOKIE_EXPRESSIONS = Arrays.asList(
      "Set-Cookie: JSESSIONID=123; Path=/hello/; secure",
      "Set-Cookie: JSESSIONID=123; Path=/hello/; HttpOnly",
      "Set-Cookie: JSESSIONID=123; Path=/hello/; secure; HttpOnly",
      "Set-Cookie: JSESSIONID=123; Path=/hello/; HttpOnly; secure"
  );

  private Pattern p;

  @Test
  public void testSetCookieSecure() {
    p = Pattern.compile("^(.*)");
    COOKIE_EXPRESSIONS.forEach(s -> assertTrue(p.matcher(s).find()));

    p = Pattern.compile("(?i)^((?:(?!;\\s?secure).)+)");
    COOKIE_EXPRESSIONS.forEach(s -> assertTrue(p.matcher(s).find()));
  }

  @Test
  public void testSetCookieHttpOnly() {
    p = Pattern.compile("^(.*)");
    COOKIE_EXPRESSIONS.forEach(s -> assertTrue(p.matcher(s).find()));

    p = Pattern.compile("(?i)^((?:(?!;\\s?HttpOnly).)+)");
    COOKIE_EXPRESSIONS.forEach(s -> assertTrue(p.matcher(s).find()));
  }

}
