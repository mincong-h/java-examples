package io.mincongh.vavr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class JavaListTest {

  /* ----- CRUD ----- */

  @Test
  public void new_ArrayList() {
    List<String> animals = new ArrayList<>();
    animals.add("🐱");
    animals.add("🐶");
    assertThat(animals).containsExactly("🐱", "🐶");
  }

  @Test
  public void new_LinkedList() {
    List<String> animals = new LinkedList<>();
    animals.add("🐱");
    animals.add("🐶");
    assertThat(animals).containsExactly("🐱", "🐶");
  }

  @Test
  public void add_ArrayList() {
    List<String> animals = new ArrayList<>();
    animals.add("🐱");
    animals.add("🐶");
    assertThat(animals).containsExactly("🐱", "🐶");
  }

  @Test
  public void add_ArraysArrayList() {
    List<String> animals = Arrays.asList("🐱", "🐶");
    try {
      animals.add("💥");
      fail();
    } catch (UnsupportedOperationException e) {
      // ok
    }
  }

  @Test
  public void add_CollectionsUnmodifiableList() {
    List<String> animals = Collections.unmodifiableList(Arrays.asList("🐱", "🐶"));
    try {
      animals.add("💥");
      fail();
    } catch (UnsupportedOperationException e) {
      // ok
    }
  }

  @Test
  public void get() {
    List<String> animals = Arrays.asList("🐱", "🐶");
    assertThat(animals.get(0)).isEqualTo("🐱");
    assertThat(animals.get(1)).isEqualTo("🐶");
    try {
      animals.get(2);
      fail();
    } catch (IndexOutOfBoundsException e) {
      // ok
    }
  }

  @Test
  public void remove_ArraysArrayList() {
    List<String> animals = Arrays.asList("🐱", "🐶");
    try {
      animals.remove("🐱"); // not type safe: Object
      fail();
    } catch (UnsupportedOperationException e) {
      // ok
    }
  }

  @Test
  public void remove_ArrayList() {
    List<String> animals = new ArrayList<>();
    animals.add("🐱");
    animals.add("🐶");
    animals.remove(true); // not type safe: Object
    assertThat(animals).containsExactly("🐱", "🐶");
    animals.remove("🐱");
    assertThat(animals).containsExactly("🐶");
  }

  @Test
  public void removeAt_ArrayList() {
    List<Integer> numbers = new ArrayList<>();
    numbers.add(2);
    numbers.add(3);
    numbers.remove(1);
    assertThat(numbers).containsExactly(2);
  }

  /* ----- Stream Operations ----- */

  @Test
  public void map() {
    List<String> animals = Arrays.asList("🐱", "🐶");
    // pattern: origin.stream().<operation>.collect(...);
    List<String> family = animals.stream().map(s -> s + s).collect(Collectors.toList());
    assertThat(family).containsExactly("🐱🐱", "🐶🐶");
  }

  @Test
  public void filter() {
    List<String> animals = Arrays.asList("🐱", "🐶");
    List<String> family = animals.stream().filter("🐱"::equals).collect(Collectors.toList());
    assertThat(family).containsExactly("🐱");
  }

  @Test
  public void flatMap() {
    List<String> cats = Arrays.asList("🐱", "🐈");
    List<String> dogs = Arrays.asList("🐶", "🐕");
    List<List<String>> lists = Arrays.asList(cats, dogs);
    List<String> animals = lists.stream().flatMap(Collection::stream).collect(Collectors.toList());
    assertThat(animals).containsExactly("🐱", "🐈", "🐶", "🐕");
  }
}
