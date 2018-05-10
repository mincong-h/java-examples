package io.mincong.ocajp.chapter3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class StringManipulationTest {

  @Test
  public void testConcatenationIfEitherOperandIsString() {
    String myStr = "abc";
    int myInt = 123;
    assertEquals("abc123", myStr + myInt);
  }

  @Test
  public void testImmutability() {
    String s1 = "1";
    String s2 = s1.concat("2");
    assertEquals("1", s1);
    assertEquals("12", s2);
  }

  @Test
  public void testStringPool() {
    String s1 = "hello";
    String s2 = "hello";
    String s3 = s1;
    assertSame(s1, s2);  // string pool is used
    assertSame(s1, s3);

    String s4 = new String("hello");  // string pool does not contain s4
    assertNotSame(s1, s4);
    assertEquals(s1, s4);
  }

  @Test
  public void testStringMethods() {
    String str = "Hello world";
    assertEquals(11, str.length());

    assertEquals('H', str.charAt(0));
    assertEquals(1, str.indexOf('e'));
    assertEquals(2, str.indexOf('l'));
    assertEquals(9, str.lastIndexOf('l'));

    assertEquals("world", str.substring(6));
    assertEquals("Hello", str.substring(0, 5));
    assertEquals("hello world", str.toLowerCase());
    assertEquals("HELLO WORLD", str.toUpperCase());
    assertTrue("hello world".equalsIgnoreCase(str));
    assertTrue("HELLO WORLD".equalsIgnoreCase(str));

    assertTrue(str.startsWith("He"));
    assertTrue(str.endsWith("ld"));
    assertTrue(str.contains("e"));

    assertEquals("Hell0 w0rld", str.replace('o', '0'));
    assertEquals("Hell0 w0rld", str.replace("o", "0"));
    assertEquals("Hello world", str.concat("  ").trim());
  }

}
