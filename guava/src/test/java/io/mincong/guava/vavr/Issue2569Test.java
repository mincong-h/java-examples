package io.mincong.guava.vavr;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** Implement diff for map https://github.com/vavr-io/vavr/issues/2569 */
public class Issue2569Test {

  @Test
  void diff() {
    var mapA =
        ImmutableMap.<String, String>builder()
            .put("a1", "1")
            .put("a2", "2")
            .put("c1", "1")
            .put("c2", "2")
            .build();
    var mapB =
        ImmutableMap.<String, String>builder()
            .put("b1", "1")
            .put("b2", "2")
            .put("c1", "1")
            .put("c2", "2")
            .build();

    var diff = Maps.difference(mapA, mapB);

    assertThat(diff.areEqual()).isFalse();
    assertThat(diff.entriesOnlyOnLeft())
        .hasSize(2)
        .containsEntry("a1", "1")
        .containsEntry("a2", "2");
    assertThat(diff.entriesOnlyOnRight())
        .hasSize(2)
        .containsEntry("b1", "1")
        .containsEntry("b2", "2");
    assertThat(diff.entriesInCommon())
        .hasSize(2)
        .containsEntry("c1", "1")
        .containsEntry("c2", "2");
  }
}
