package io.mincong.ocpjp.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class CollectionTest {

  private List<Book> books;

  @Before
  public void setUp() throws Exception {
    books = new ArrayList<>();
    books.add(new Book("A"));
    books.add(new Book("B"));
  }

  /* Collection-related tests */

  @Test
  public void forEach_list() throws Exception {
    StringBuilder sb1 = new StringBuilder();
    books.forEach(sb1::append);
    assertThat(sb1.toString()).isEqualTo("AB");

    StringBuilder sb2 = new StringBuilder();
    books.forEach(b -> sb2.append(b.getName()).append(' '));
    assertThat(sb2.toString()).isEqualTo("A B ");
  }

  @Test
  public void forEach_streams() throws Exception {
    StringBuilder sb = new StringBuilder();
    Stream.of(new Book("A"), new Book("B"))
        .forEach(sb::append);
    assertThat(sb.toString()).isEqualTo("AB");
  }

  @Test
  public void createPipeline() throws Exception {
    List<String> names = books.stream()
        .map(Book::getName)
        .collect(Collectors.toList());
    assertThat(names).containsExactly("A", "B");
  }

  @Test
  public void filterCollection() throws Exception {
    List<Book> filtered = books.stream()
        .filter(b -> b.getName().startsWith("A"))
        .collect(Collectors.toList());
    assertThat(filtered).containsExactly(new Book("A"));
  }

  @Test
  public void filter() throws Exception {
    Map<String, Integer> m = new HashMap<>();
    m.put("A1", 1);
    m.put("A2", 2);
    m.put("B1", 3);

    Map<String, Integer> subMap = m.entrySet()
        .stream()
        .filter(e -> e.getKey().startsWith("A"))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    assertThat(subMap).containsOnly(entry("A1", 1), entry("A2", 2));
  }

  /* Stream API related tests */

  @Test
  public void peek() throws Exception {
    List<Integer> positives = new ArrayList<>();
    int sum = Stream.of(-1, 0, 1, 2, 3)
        .filter(i -> i > 0)
        .peek(positives::add)
        .mapToInt(Integer::intValue)
        .sum();

    assertThat(positives).containsExactly(1, 2, 3);
    assertThat(sum).isEqualTo(6);
  }

  @Test
  public void map() throws Exception {
    String s = Stream.of(-1, 0, 1, 2, 3)
        .filter(i -> i > 0)
        .map(String::valueOf)
        .collect(Collectors.joining(", "));
    assertThat(s).isEqualTo("1, 2, 3");
  }

  @Test
  public void findFirst_normalStream() throws Exception {
    Optional<String> first = Stream.of("A", "B").findFirst();
    assertThat(first.isPresent()).isTrue();
    assertThat(first.orElse("C")).isEqualTo("A");
  }

  @Test
  public void findFirst_emptyStream() throws Exception {
    Optional<String> first = new ArrayList<String>().stream().findFirst();
    assertThat(first.isPresent()).isFalse();
  }

  @Test
  public void findAny_normalStream() throws Exception {
    Optional<String> any = Stream.of("A", "B").findAny();
    assertThat(any.isPresent()).isTrue();
  }

  @Test
  public void findAny_emptyStream() throws Exception {
    Optional<String> any = new ArrayList<String>().stream().findAny();
    assertThat(any.isPresent()).isFalse();
  }

  @Test
  public void anyMatch() throws Exception {
    boolean hasMatched = Stream.of(-1, -2, 3).anyMatch(i -> i > 0);
    assertThat(hasMatched).isTrue();
  }

  @Test
  public void allMatch() throws Exception {
    assertThat(Stream.of(-1, -2, 3).allMatch(i -> i > 0)).isFalse();
    assertThat(Stream.of(-1, -2, 3).allMatch(i -> i < 4)).isTrue();
  }

  @Test
  public void noneMatch() throws Exception {
    assertThat(Stream.of(-1, -2, 3).noneMatch(i -> i == 0)).isTrue();
    assertThat(Stream.of(-1, -2, 3).noneMatch(i -> i < 10)).isFalse();
  }

  @Test
  public void count() throws Exception {
    assertThat(Stream.of(1, 2, 3).count()).isEqualTo(3);
  }

  @Test
  public void distinct() throws Exception {
    assertThat(Stream.of(1, 2, 1).distinct().count()).isEqualTo(2);
  }

  @Test
  public void sorted_naturalOrder() throws Exception {
    List<Integer> numbers = Stream.of(1, 3, 2)
        .sorted()
        .collect(Collectors.toList());
    assertThat(numbers).containsExactly(1, 2, 3);
  }

  @Test
  public void sorted_specificOrder1() throws Exception {
    List<Integer> numbers = Stream.of(1, 3, 2)
        .sorted(Comparator.comparing(Integer::intValue).reversed())
        .collect(Collectors.toList());
    assertThat(numbers).containsExactly(3, 2, 1);
  }

  @Test
  public void sorted_specificOrder2() throws Exception {
    List<Book> numbers = books.stream()
        .sorted(Comparator.comparing(Book::getName))
        .collect(Collectors.toList());
    assertThat(numbers).containsExactly(new Book("A"), new Book("B"));
  }

  @Test
  public void limit() throws Exception {
    List<Integer> numbers = Stream.of(1, 2, 3)
        .limit(2)
        .collect(Collectors.toList());
    assertThat(numbers).containsExactly(1, 2);
  }

  @Test
  public void skip() throws Exception {
    List<Integer> numbers = Stream.of(-2, -1, 1, 2, 3)
        .skip(2)
        .collect(Collectors.toList());
    assertThat(numbers).containsExactly(1, 2, 3);
  }

  @Test
  public void reduce() throws Exception {
    assertThat(Stream.of(1, 2, 3).reduce((x, y) -> x + y)).isPresent().hasValue(6);
    assertThat(Stream.of(1, 2, 3).reduce(Math::max)).isPresent().hasValue(3);
    assertThat(Stream.of(1, 2, 3).reduce(4, Math::max)).isEqualTo(4);
  }

  @Test
  public void intStream_average() throws Exception {
    assertThat(IntStream.range(0, 3).average()).isPresent().hasValue(1);
    assertThat(IntStream.rangeClosed(0, 2).average()).isPresent().hasValue(1);
  }

  @Test
  public void iterate() throws Exception {
    List<Integer> numbers = Stream.iterate(0, n -> n + 10).limit(5).collect(Collectors.toList());
    assertThat(numbers).containsExactly(0, 10, 20, 30, 40);
  }

  @Test
  public void flatMap() throws Exception {
    List<String> chars = Stream.of("Hello", "Java")
        .map(s -> s.split(""))
        .flatMap(Arrays::stream)
        .distinct()
        .sorted()
        .collect(Collectors.toList());
    assertThat(chars).containsExactly("H", "J", "a", "e", "l", "o", "v");
  }

  @Test
  public void collector_set() throws Exception {
    Set<Integer> numbers = Stream.of(1, 2, 2).collect(Collectors.toSet());
    assertThat(numbers).containsExactly(1, 2);
  }

  @Test
  public void collect_map() throws Exception {
    Map<String, Integer> m = Stream.of(1, 2)
        .collect(Collectors.toMap(String::valueOf, Function.identity()));
    assertThat(m).containsOnly(entry("1", 1), entry("2", 2));
  }

  @Test
  public void groupBy() throws Exception {
    Map<Character, Set<String>> m = Stream.of("A1", "A2", "A1", "B1", "B2")
        .collect(Collectors.groupingBy(s -> s.charAt(0), HashMap::new, Collectors.toSet()));
    assertThat(m.get('A')).containsExactlyInAnyOrder("A1", "A2");
    assertThat(m.get('B')).containsExactlyInAnyOrder("B1", "B2");
  }

  @Test
  public void partitioningBy() throws Exception {
    Map<Boolean, List<Integer>> isOdd = Stream.of(0, 1, 2, 3)
        .collect(Collectors.partitioningBy(i -> i % 2 == 0));
    assertThat(isOdd.get(true)).containsExactly(0, 2);
    assertThat(isOdd.get(false)).containsExactly(1, 3);
  }

  private static class Book {

    private String name;

    Book(String name) {
      this.name = name;
    }

    String getName() {
      return name;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Book)) {
        return false;
      }

      Book book = (Book) o;

      return name.equals(book.name);
    }

    @Override
    public int hashCode() {
      return name.hashCode();
    }

    @Override
    public String toString() {
      return name;
    }
  }

}
