package io.mincongh.java8.so2607289;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
class ConvertArrayToListTest {

  private final Integer NEW_ITEM = 4;

  private final Integer OLD_ITEM = 1;

  @Test
  void testConversionUsingArrays1() {
    Integer[] array = {1, 2, 3};
    List<Integer> list = Arrays.asList(array);

    assertListValues(list);
    assertOperationUnsupported(list, l -> l.add(NEW_ITEM));
    assertOperationUnsupported(list, l -> l.remove(OLD_ITEM));
  }

  @Test
  void testConversionUsingArrays2() {
    List<Integer> list = Arrays.asList(1, 2, 3);

    assertListValues(list);
    assertOperationUnsupported(list, l -> l.add(NEW_ITEM));
    assertOperationUnsupported(list, l -> l.remove(OLD_ITEM));
  }

  @Test
  void testConversionUsingArrayList() {
    List<Integer> list = new ArrayList<>();
    for (int i : new int[] {1, 2, 3}) {
      list.add(i);
    }

    assertListValues(list);
    assertOperationSupported(list, l -> l.add(NEW_ITEM));
    assertOperationSupported(list, l -> l.remove(OLD_ITEM));
  }

  @Test
  void testConversionUsingStream1() {
    List<Integer> list = Stream.of(1, 2, 3).collect(Collectors.toList());

    assertListValues(list);
    assertOperationSupported(list, l -> l.add(NEW_ITEM));
    assertOperationSupported(list, l -> l.remove(OLD_ITEM));
  }

  @Test
  void testConversionUsingStream2() {
    List<Integer> list = Stream.of(1, 2, 3).collect(Collectors.toCollection(LinkedList::new));

    assertListValues(list);
    assertOperationSupported(list, l -> l.add(NEW_ITEM));
    assertOperationSupported(list, l -> l.remove(OLD_ITEM));
  }

  private void assertListValues(List<Integer> list) {
    assertEquals(1, list.get(0).intValue());
    assertEquals(2, list.get(1).intValue());
    assertEquals(3, list.get(2).intValue());
  }

  private <T> void assertOperationSupported(List<T> list, Predicate<List<T>> predicate) {
    predicate.test(list);
  }

  private <T> void assertOperationUnsupported(List<T> list, Predicate<List<T>> predicate) {
    try {
      predicate.test(list);
      fail();
    } catch (UnsupportedOperationException e) {
      // Ok
    }
  }
}
