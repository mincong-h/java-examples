package io.mincongh.vavr;

import static org.assertj.core.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class JavaMapTest {

  private Map<String, String> map;

  @BeforeEach
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
        map.entrySet().stream()
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
  public void immutable() {
    List<String> immutableList = Collections.singletonList("🤔");
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> immutableList.add("😱"));
  }
}
