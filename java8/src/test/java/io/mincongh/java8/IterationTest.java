package io.mincongh.java8;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * Internal vs External Iteration: {@code for} vs. {@code foreach}
 *
 * @author Mincong Huang
 */
public class IterationTest {

  private final List<String> words = Arrays.asList("Hello", "Java", "8");

  @Test
  public void testFor() {
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(word).append(" ");
    }
    assertEquals("Hello Java 8", sb.toString().trim());
  }

  @Test
  public void testForEach() {
    StringBuilder sb = new StringBuilder();
    words.forEach(word -> sb.append(word).append(" "));
    assertEquals("Hello Java 8", sb.toString().trim());
  }

  @Test
  public void testStream() {
    String sentence = words.stream().collect(Collectors.joining(" "));
    assertEquals("Hello Java 8", sentence);
  }
}
