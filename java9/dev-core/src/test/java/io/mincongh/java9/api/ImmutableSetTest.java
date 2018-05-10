package io.mincongh.java9.api;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests immutable set.
 *
 * @author Mincong Huang
 */
public class ImmutableSetTest {

  @Test
  public void createImmutableSet() {
    Set<String> words = Set.of("Hello", "Java9");
    assertTrue(words.contains("Hello"));
    assertTrue(words.contains("Java9"));
  }

  @Test
  public void immutability() {
    Set<String> words = Set.of("Hello", "Java9");
    // Some of the unsupported methods
    List<Consumer<String>> consumers =
        List.of(
            words::add,
            words::remove,
            s -> words.addAll(List.of(s)),
            s -> words.removeAll(List.of(s)),
            s -> words.retainAll(List.of(s)));

    consumers.forEach(this::assertThrown);
  }

  private void assertThrown(Consumer<String> consumer) {
    try {
      consumer.accept("Hello");
      fail("The operation should not be accepted.");
    } catch (UnsupportedOperationException e) {
      // Ok
    }
  }
}
