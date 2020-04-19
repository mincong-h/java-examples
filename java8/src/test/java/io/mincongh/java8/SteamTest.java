package io.mincongh.java8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the method {@code stream()} in Java 8.
 *
 * @author Mincong Huang
 */
public class SteamTest {

  private final List<Integer> numbers = Arrays.asList(-100, 5, 10, 15, 20, 50);

  @Test
  void testSequentialStream() {
    int sum = numbers.stream().filter(i -> i > 0).reduce(0, Integer::sum);
    assertEquals(100, sum);
  }

  @Test
  void testParallelStream() {
    int sum1 = numbers.parallelStream().reduce(0, Integer::sum);
    int sum2 = numbers.stream().parallel().reduce(0, Integer::sum);
    assertEquals(0, sum1);
    assertEquals(0, sum2);
  }

  @Test
  void testMap() {
    double average =
        Stream.of("a1", "a2", "a3")
            .map(s -> s.substring(1))
            .mapToInt(Integer::parseInt)
            .average()
            .getAsDouble();
    assertEquals(2.0, average, 0.0000001);
  }

  @Test
  void testCollection() {
    Set<Integer> numbers =
        Stream.of(-100, 1, 2, 3).filter(i -> i > 0).collect(Collectors.toCollection(HashSet::new));
    assertTrue(numbers.contains(1));
    assertTrue(numbers.contains(2));
    assertTrue(numbers.contains(3));
    assertTrue(!numbers.contains(-100));
  }
}
