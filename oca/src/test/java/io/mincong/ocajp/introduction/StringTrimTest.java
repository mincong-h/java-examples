package io.mincong.ocajp.introduction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test method {@code String#trim()}.
 *
 * @author Mincong HUANG
 */
public class StringTrimTest {

  @Test
  public void testStringTrim() {
    String str = "Java Duke ";
    int length = str.trim().length();
    assertEquals(9, length);
  }
}
