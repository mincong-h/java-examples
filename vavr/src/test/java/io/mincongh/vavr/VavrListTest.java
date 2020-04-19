package io.mincongh.vavr;

import io.vavr.collection.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 * @since 1.0
 */
class VavrListTest {

  /* ----- CRUD ----- */

  @Test
  void of() {
    List<String> animals = List.of("🐱", "🐶");
    assertThat(animals).containsExactly("🐱", "🐶");

    List<String> another = List.ofAll(animals);
    assertThat(another).containsExactly("🐱", "🐶");

    List<String> empty = List.empty();
    assertThat(empty).isEmpty();
  }

  @Test
  void append() {
    List<String> animals = List.of("🐱", "🐶");
    List<String> another = animals.append("😌");
    assertThat(another).containsExactly("🐱", "🐶", "😌"); // immutable
  }

  @Test
  void prepend() {
    List<String> animals = List.of("🐱", "🐶");
    List<String> another = animals.prepend("🙂");
    assertThat(animals).containsExactly("🐱", "🐶");
    assertThat(another).containsExactly("🙂", "🐱", "🐶");
  }

  @Test
  void get() {
    List<String> animals = List.of("🐱", "🐶");
    assertThat(animals.get()).isEqualTo("🐱");
    assertThat(animals.get(0)).isEqualTo("🐱");
    assertThat(animals.get(1)).isEqualTo("🐶");
  }

  @Test
  void head() {
    List<String> animals = List.of("🐱", "🐶");
    assertThat(animals.head()).isEqualTo("🐱");
  }

  @Test
  void last() {
    List<String> animals = List.of("🐱", "🐶");
    assertThat(animals.last()).isEqualTo("🐶");
  }

  @Test
  void remove() {
    List<String> animals = List.of("🐱", "🐶");
    List<String> another = animals.remove("🐱"); // type safe: T
    assertThat(another).containsExactly("🐶");
  }

  @Test
  void removeAt() {
    List<Integer> numbers = List.of(2, 3);
    List<Integer> another = numbers.removeAt(1);
    assertThat(another).containsExactly(2);
  }

  /* ----- Stream Operations ----- */

  @Test
  void map() {
    // implicit stream()
    // shortcut for collect
    List<String> animals = List.of("🐱", "🐶");
    List<String> family = animals.map(s -> s + s);
    assertThat(family).containsExactly("🐱🐱", "🐶🐶");
  }

  @Test
  void filter() {
    List<String> animals = List.of("🐱", "🐶");
    List<String> family = animals.filter("🐱"::equals);
    assertThat(family).containsExactly("🐱");
  }

  @Test
  void flatMap() {
    List<String> cats = List.of("🐱", "🐈");
    List<String> dogs = List.of("🐶", "🐕");
    List<List<String>> lists = List.of(cats, dogs);
    List<String> list = lists.flatMap(Function.identity());
    assertThat(list).containsExactly("🐱", "🐈", "🐶", "🐕");
  }

  /* ----- Javadoc ----- */

  @Test
  void javadoc_of() {
    List<Integer> list1 = List.of(1, 2, 3, 4);
    List<Integer> list2 = List.of(4).prepend(3).prepend(2).prepend(1);
    assertThat(list1.equals(list2)).isTrue();
  }
}
