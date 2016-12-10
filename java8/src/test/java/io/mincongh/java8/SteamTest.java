package io.mincongh.java8;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test the method {@code stream()} in Java 8.
 *
 * @author Mincong Huang
 */
public class SteamTest {

  private final List<Integer> numbers = Arrays.asList(-100, 5, 10, 15, 20, 50);

  @Test
  public void testSequentialSum() {
    int sum = numbers.stream().filter(i -> i > 0).reduce(0, Integer::sum);
    assertEquals(100, sum);
  }

  @Test
  public void testParallelSum() {
    int sum1 = numbers.parallelStream().reduce(0, Integer::sum);
    assertEquals(0, sum1);
    int sum2 = numbers.stream().parallel().reduce(0, Integer::sum);
    assertEquals(0, sum2);
  }
}
