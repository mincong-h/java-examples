package io.mincongh.vavr;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import java.util.function.Function;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class VavrMapTest {

  private Map<String, String> map;

  @Before
  public void setUp() {
    map = HashMap.of("cat", "🐱", "dog", "🐶");
  }

  @Test
  public void hashMap_of() {
    assertThat(map).containsExactlyInAnyOrder(Tuple.of("cat", "🐱"), Tuple.of("dog", "🐶"));
  }

  @Test
  public void hashMap_ofAll() {
    map = HashMap.ofAll(map.toJavaMap());
    assertThat(map).containsExactlyInAnyOrder(Tuple.of("cat", "🐱"), Tuple.of("dog", "🐶"));
  }

  @Test
  public void hashMap_ofEntries() {
    map = HashMap.ofEntries(Tuple.of("cat", "🐱"), Tuple.of("dog", "🐶"));
    assertThat(map).containsExactlyInAnyOrder(Tuple.of("cat", "🐱"), Tuple.of("dog", "🐶"));
  }

  @Test
  public void hashMap_get() {
    Option<String> cat = map.get("cat");
    assertThat(cat.isDefined()).isTrue();
    assertThat(cat.get()).isEqualTo("🐱");

    // no side effect
    Option<String> duck = map.get("duck");
    assertThat(duck.isEmpty()).isTrue();
  }

  @Test
  public void hashMap_forLoop() {
    java.util.List<String> list = new java.util.ArrayList<>();
    for (Tuple2<String, String> t : map) {
      list.add(t._1 + ": " + t._2);
    }
    assertThat(list).containsExactlyInAnyOrder("cat: 🐱", "dog: 🐶");
  }

  @Test
  public void hashMap_stream() {
    // implicit stream()
    // shortcut for collect
    List<String> list = map.map(t -> t._1 + ": " + t._2).toList();
    assertThat(list).containsExactlyInAnyOrder("cat: 🐱", "dog: 🐶");
  }

  @Test
  public void stream_range() {
    List<String> strings =
        Stream.range(0, 3) //
            .map(String::valueOf)
            .toList();
    assertThat(strings).containsExactly("0", "1", "2");
  }

  @Test
  public void stream_flatMap() {
    List<String> cats = List.of("🐱", "🐈");
    List<String> dogs = List.of("🐶", "🐕");
    List<List<String>> animalLists = List.of(cats, dogs);
    List<String> animals = animalLists.flatMap(Function.identity()).toList();
    assertThat(animals).containsExactly("🐱", "🐈", "🐶", "🐕");
  }

  @Test
  public void immutable() {
    List<String> immutableList = List.of("🤔");
    List<String> anotherList = immutableList.append("😅");
    assertThat(immutableList).containsExactly("🤔");
    assertThat(anotherList).containsExactly("🤔", "😅");
  }
}
