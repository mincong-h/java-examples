package io.mincongh.generic;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class GenericHelperTest {

  @Test
  public void testToList() {
    List<String> words = GenericHelper.toList("Hello", "World");
    assertEquals(words.get(0), "Hello");
    assertEquals(words.get(1), "World");
  }

  @Test
  public void testBuildMap() {
    Map<String, Integer> scores = GenericHelper.buildMap("Player Tom", 100);
    assertEquals(1, scores.entrySet().size());
    assertEquals(100, scores.get("Player Tom").intValue());
  }

  @Test
  public void testGetClass() {
    assertEquals(String.class, GenericHelper.getClass("Hello"));
  }
}
