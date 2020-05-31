package io.mincongh.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Demonstrate the limit of different interfaces related to collection.
 *
 * @author Mincong Huang
 */
class CollectionTest {

  private final Collection<Integer> collection = Arrays.asList(1, 2, 3);
  private final ArrayList<Integer> arrayList = new ArrayList<>(collection);
  private final LinkedList<Integer> linkedList = new LinkedList<>(collection);
  private final HashSet<Integer> hashSet = new HashSet<>(collection);
  private final Stack<Integer> stack = new Stack<>();

  @BeforeEach
  void setUp() {
    collection.forEach(i -> stack.push(i));
  }

  @Test
  void testListAsInterface() {
    assertEquals(6, CollectionHelper.sumOfList(arrayList));
    assertEquals(6, CollectionHelper.sumOfList(linkedList));
  }

  @Test
  void testCollectionAsInterface() {
    assertEquals(6, CollectionHelper.sumOfCollection(collection));
    assertEquals(6, CollectionHelper.sumOfCollection(arrayList));
    assertEquals(6, CollectionHelper.sumOfCollection(linkedList));
    assertEquals(6, CollectionHelper.sumOfCollection(hashSet));
    assertEquals(6, CollectionHelper.sumOfCollection(stack));
    assertEquals(3, stack.size());
  }

  @Test
  void testIterableAsInterface() {
    assertEquals(6, CollectionHelper.sumOfIterable(collection));
    assertEquals(6, CollectionHelper.sumOfIterable(arrayList));
    assertEquals(6, CollectionHelper.sumOfIterable(linkedList));
    assertEquals(6, CollectionHelper.sumOfIterable(hashSet));
    assertEquals(6, CollectionHelper.sumOfIterable(stack));
  }
}
