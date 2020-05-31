package io.mincongh.generic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
class GenericHelperTest {

  @Test
  void toList() {
    List<String> words = GenericHelper.toList("Hello", "World");
    assertEquals("Hello", words.get(0));
    assertEquals("World", words.get(1));
  }

  @Test
  void buildMap_keyString_valueInt() {
    Map<String, Integer> words = GenericHelper.buildMap("one", 1);
    assertEquals(1, words.entrySet().size());
    assertEquals(1, words.get("one").intValue());
  }

  @Test
  void buildMap_keyInt_valueString() {
    Map<Integer, String> words = GenericHelper.buildMap(1, "one");
    assertEquals(1, words.entrySet().size());
    assertEquals("one", words.get(1));
  }

  @Test
  void getClassByType() {
    assertEquals(String.class, GenericHelper.getClass("Hello"));
    assertEquals(Integer.class, GenericHelper.getClass(1));
    assertEquals(Long.class, GenericHelper.getClass(1L));
  }
}
