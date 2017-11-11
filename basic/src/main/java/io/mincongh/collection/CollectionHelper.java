package io.mincongh.collection;

import java.util.Collection;
import java.util.List;

/**
 * This helper class helps to compare the difference of each interface as input.
 *
 * @author Mincong Huang
 */
public class CollectionHelper {

  private CollectionHelper() {
    // Utility class, do not instantiate
  }

  public static int sumOfList(List<Integer> numbers) {
    int sum = 0;
    for (int number : numbers) {
      sum += number;
    }
    return sum;
  }

  public static int sumOfCollection(Collection<Integer> numbers) {
    int sum = 0;
    for (int number : numbers) {
      sum += number;
    }
    return sum;
  }

  public static int sumOfIterable(Iterable<Integer> numbers) {
    int sum = 0;
    for (int number : numbers) {
      sum += number;
    }
    return sum;
  }
}
