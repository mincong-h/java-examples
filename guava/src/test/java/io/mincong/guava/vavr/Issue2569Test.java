package io.mincong.guava.vavr;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Implement diff for map
 *
 * <p>https://github.com/vavr-io/vavr/issues/2569
 */
public class Issue2569Test {

  @Test
  void diff() {
    Map<String, String> mapA =
        ImmutableMap.<String, String>builder().put("a1", "1").put("c1", "1").put("c2", "2").build();
    Map<String, String> mapB =
        ImmutableMap.<String, String>builder().put("b1", "1").put("c1", "1").put("c2", "3").build();

    MapDifference<String, String> diff = Maps.difference(mapA, mapB);

    boolean areEqual = diff.areEqual();
    assertThat(areEqual).isFalse();

    Map<String, String> entriesOnlyOnLeft = diff.entriesOnlyOnLeft();
    assertThat(entriesOnlyOnLeft).hasSize(1).containsEntry("a1", "1");

    Map<String, String> entriesOnlyOnRight = diff.entriesOnlyOnRight();
    assertThat(entriesOnlyOnRight).hasSize(1).containsEntry("b1", "1");

    Map<String, String> entriesInCommon = diff.entriesInCommon();
    assertThat(entriesInCommon).hasSize(1).containsEntry("c1", "1");

    Map<String, ValueDifference<String>> entriesDiffering = diff.entriesDiffering();
    assertThat(entriesDiffering.keySet()).containsExactly("c2");
    assertThat(entriesDiffering.get("c2"))
        .satisfies(
            d -> {
              assertThat(d.leftValue()).isEqualTo("2");
              assertThat(d.rightValue()).isEqualTo("3");
            });
  }
}
