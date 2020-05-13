package io.mincongh.vavr;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Implement diff for map
 *
 * <p>https://github.com/vavr-io/vavr/issues/2569
 */
public class Issue2569Test {

  @Test
  void diff() {
    Map<String, String> mapA = HashMap.of("a1", "1", "c1", "1", "c2", "2");
    Map<String, String> mapB = HashMap.of("b1", "1", "c1", "1", "c2", "3");

    boolean areEqual = mapA.equals(mapB);
    assertThat(areEqual).isFalse();
    assertThat(mapA.equals(HashMap.of("a1", "1", "c1", "1", "c2", "2"))).isTrue();

    Map<String, String> entriesOnlyOnLeft = mapA.removeAll(mapB.keySet());
    assertThat(entriesOnlyOnLeft).isEqualTo(HashMap.of("a1", "1"));

    Map<String, String> entriesOnlyOnRight = mapB.removeAll(mapA.keySet());
    assertThat(entriesOnlyOnRight).isEqualTo(HashMap.of("b1", "1"));

    Map<String, String> entriesInCommon = mapA.retainAll(mapB);
    assertThat(entriesInCommon).isEqualTo(HashMap.of("c1", "1"));

    // no equivalent API of ``entriesDiffering'' of Guava.
  }
}
