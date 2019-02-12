package io.mincongh.vavr;

import io.vavr.collection.List;
import java.util.function.Function;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class VavrListTest {

  /* ----- CRUD ----- */

  @Test
  public void of() {
    List<String> animals = List.of("🐱", "🐶");
    assertThat(animals).containsExactly("🐱", "🐶");
  }

  @Test
  public void append() {
    List<String> animals = List.of("🐱", "🐶");
    List<String> another = animals.append("😌");
    assertThat(another).containsExactly("🐱", "🐶", "😌"); // immutable
  }

  @Test
  public void prepend() {
    List<String> animals = List.of("🐱", "🐶");
    List<String> another = animals.prepend("🙂");
    assertThat(another).containsExactly("🙂", "🐱", "🐶");
  }

  @Test
  public void get() {
    List<String> animals = List.of("🐱", "🐶");
    assertThat(animals.get()).isEqualTo("🐱");
    assertThat(animals.get(0)).isEqualTo("🐱");
    assertThat(animals.get(1)).isEqualTo("🐶");
  }

  @Test
  public void remove() {
    List<String> animals = List.of("🐱", "🐶");
    List<String> another = animals.remove("🐱"); // type safe: T
    assertThat(another).containsExactly("🐶");
  }

  @Test
  public void removeAt() {
    List<Integer> numbers = List.of(2, 3);
    List<Integer> another = numbers.removeAt(1);
    assertThat(another).containsExactly(2);
  }

  /* ----- Stream Operations ----- */

  @Test
  public void map() {
    // implicit stream()
    // shortcut for collect
    List<String> animals = List.of("🐱", "🐶");
    List<String> family = animals.map(s -> s + s);
    assertThat(family).containsExactly("🐱🐱", "🐶🐶");
  }

  @Test
  public void filter() {
    List<String> animals = List.of("🐱", "🐶");
    List<String> family = animals.filter("🐱"::equals);
    assertThat(family).containsExactly("🐱");
  }

  @Test
  public void flatMap() {
    List<String> cats = List.of("🐱", "🐈");
    List<String> dogs = List.of("🐶", "🐕");
    List<List<String>> lists = List.of(cats, dogs);
    List<String> list = lists.flatMap(Function.identity());
    assertThat(list).containsExactly("🐱", "🐈", "🐶", "🐕");
  }
}
