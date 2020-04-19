package io.mincongh.vavr;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.*;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 * @since 1.0
 */
class VavrMapTest {

  private Map<String, String> map;

  @BeforeEach
  void setUp() {
    map = HashMap.of("cat", "🐱", "dog", "🐶");
  }

  @Test
  void hashMap_of() {
    assertThat(map).containsExactlyInAnyOrder(Tuple.of("cat", "🐱"), Tuple.of("dog", "🐶"));
  }

  @Test
  void hashMap_ofAll() {
    map = HashMap.ofAll(map.toJavaMap());
    assertThat(map).containsExactlyInAnyOrder(Tuple.of("cat", "🐱"), Tuple.of("dog", "🐶"));
  }

  @Test
  void hashMap_ofEntries() {
    map = HashMap.ofEntries(Tuple.of("cat", "🐱"), Tuple.of("dog", "🐶"));
    assertThat(map).containsExactlyInAnyOrder(Tuple.of("cat", "🐱"), Tuple.of("dog", "🐶"));
  }

  @Test
  void hashMap_get() {
    Option<String> cat = map.get("cat");
    assertThat(cat.isDefined()).isTrue();
    assertThat(cat.get()).isEqualTo("🐱");

    // no side effect
    Option<String> duck = map.get("duck");
    assertThat(duck.isEmpty()).isTrue();
  }

  @Test
  void hashMap_forLoop() {
    java.util.List<String> list = new java.util.ArrayList<>();
    for (Tuple2<String, String> t : map) {
      list.add(t._1 + ": " + t._2);
    }
    assertThat(list).containsExactlyInAnyOrder("cat: 🐱", "dog: 🐶");
  }

  @Test
  void hashMap_stream() {
    // implicit stream()
    // shortcut for collect
    List<String> list = map.map(t -> t._1 + ": " + t._2).toList();
    assertThat(list).containsExactlyInAnyOrder("cat: 🐱", "dog: 🐶");
  }

  @Test
  void stream_range() {
    List<String> strings =
        Stream.range(0, 3) //
            .map(String::valueOf)
            .toList();
    assertThat(strings).containsExactly("0", "1", "2");
  }
}
