package io.mincongh.vavr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.entry;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class JavaMapTest {

  private Map<String, String> map;

  @Before
  public void setUp() {
    map = new HashMap<>();
    map.put("cat", "🐱");
    map.put("dog", "🐶");
  }

  @Test
  public void hashMap_creation() {
    assertThat(map).containsExactly(entry("cat", "🐱"), entry("dog", "🐶"));
  }

  @Test
  public void hashMap_get() {
    String cat = map.get("cat");
    assertThat(cat).isEqualTo("🐱");

    // side effect
    String duck = map.get("duck");
    assertThatNullPointerException().isThrownBy(() -> duck.isEmpty());
  }

  @Test
  public void hashMap_forLoop() {
    List<String> list = new ArrayList<>();
    for (Map.Entry<String, String> e : map.entrySet()) {
      list.add(e.getKey() + ": " + e.getValue());
    }
    assertThat(list).containsExactlyInAnyOrder("cat: 🐱", "dog: 🐶");
  }

  @Test
  public void hashMap_stream() {
    List<String> list =
        map.entrySet()
            .stream()
            .map(e -> e.getKey() + ": " + e.getValue())
            .collect(Collectors.toList());
    assertThat(list).containsExactlyInAnyOrder("cat: 🐱", "dog: 🐶");
  }

  @Test
  public void stream_range() {
    List<String> strings =
        IntStream.range(0, 3) //
            .mapToObj(String::valueOf)
            .collect(Collectors.toList());
    assertThat(strings).containsExactly("0", "1", "2");
  }

  @Test
  public void stream_flatMap() {
    List<String> cats = Arrays.asList("🐱", "🐈");
    List<String> dogs = Arrays.asList("🐶", "🐕");
    List<List<String>> animalLists = Arrays.asList(cats, dogs);
    List<String> animals =
        animalLists //
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    assertThat(animals).containsExactly("🐱", "🐈", "🐶", "🐕");
  }

  @Test
  public void immutable() {
    List<String> immutableList = Collections.singletonList("🤔");
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> immutableList.add("😱"));
  }
}
