package io.mincong.ocajp.chapter3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class StringBuilderTest {

  /**
   * {@code StringBuilder} is mutable and can use chaining.
   */
  @Test
  public void testMutabilityAndChaining() {
    StringBuilder builderA = new StringBuilder();
    StringBuilder builderB = builderA.append("a");
    assertEquals("a", builderA.toString());
    assertEquals("a", builderB.toString());

    builderB.append("b");
    assertEquals("ab", builderA.toString());
    assertEquals("ab", builderB.toString());
    assertSame(builderA, builderB);

    builderB.append("c").append("d");
    assertEquals("abcd", builderB.toString());
  }

  @Test
  public void testCreation() {
    StringBuilder builderA = new StringBuilder();
    assertTrue(builderA.capacity() > 0);
    assertEquals(0, builderA.length());

    StringBuilder builderB = new StringBuilder("abc");
    assertTrue(builderB.capacity() > 3);
    assertEquals(3, builderB.length());

    StringBuilder builderC = new StringBuilder(3);
    assertEquals(3, builderC.capacity());
    assertEquals(0, builderC.length());
    builderC.append("abc");
    assertEquals(3, builderC.capacity());
    assertEquals(3, builderC.length());
    builderC.append("d");
    assertEquals(4, builderC.length());
  }

  /**
   * Test methods {@code indexOf}, {@code length}, {@code charAt}, and {@code substring}.
   */
  @Test
  public void testBasicMethods() {
    StringBuilder builder = new StringBuilder("animals");
    String sub = builder.substring(builder.indexOf("a"), builder.indexOf("al"));
    assertEquals("anim", sub);
    assertEquals(7, builder.length());
    assertEquals('s', builder.charAt(6));
  }

  @Test
  public void testAppend() {
    StringBuilder builder = new StringBuilder().append(1).append('c');
    builder.append("-").append(true);
    assertEquals("1c-true", builder.toString());
  }

  @Test
  public void testInsert() {
    StringBuilder builder = new StringBuilder("animals");
    builder.insert(7, "-");
    assertEquals("animals-", builder.toString());

    builder.insert(0, "-");
    assertEquals("-animals-", builder.toString());

    builder.insert(7, "-");
    assertEquals("-animal-s-", builder.toString());
  }

  @Test
  public void testDelete() {
    StringBuilder builder = new StringBuilder("delete me");
    builder.delete(0, 6);
    assertEquals("The 'endIndex' = 6 should be exclusive.", " me", builder.toString());

    builder.deleteCharAt(0);
    assertEquals("me", builder.toString());
  }

  @Test
  public void testReverse() {
    StringBuilder builder = new StringBuilder("ah");
    assertEquals("ha", builder.reverse().toString());
  }

  @Test
  public void testEquals() {
    StringBuilder builder1 = new StringBuilder();
    StringBuilder builder2 = new StringBuilder();
    StringBuilder builder3 = builder1.append("a");

    assertNotSame(builder1, builder2);
    assertSame(builder1, builder3);

    String str1 = "Hello world";
    String str2 = "Hello world";
    assertSame("String pool is used here, so str1 & str2 should be the same", str1, str2);
  }

}
