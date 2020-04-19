package io.mincongh.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Internal vs External Iteration: {@code for} vs. {@code foreach}
 *
 * @author Mincong Huang
 */
class IterationTest {

  private final List<String> words = Arrays.asList("Hello", "Java", "8");

  @Test
  void testFor() {
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(word).append(" ");
    }
    assertEquals("Hello Java 8", sb.toString().trim());
  }

  @Test
  void testForEach() {
    StringBuilder sb = new StringBuilder();
    words.forEach(word -> sb.append(word).append(" "));
    assertEquals("Hello Java 8", sb.toString().trim());
  }

  @Test
  void testStream() {
    String sentence = words.stream().collect(Collectors.joining(" "));
    assertEquals("Hello Java 8", sentence);
  }
}
