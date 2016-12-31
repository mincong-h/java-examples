package io.mincongh.collection;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

/**
 * Demonstrate the limit of different interfaces related to collection.
 *
 * @author Mincong Huang
 */
public class CollectionTest {

  private final Collection<Integer> collection = Arrays.asList(1, 2, 3);
  private final ArrayList<Integer> arrayList = new ArrayList<>(collection);
  private final LinkedList<Integer> linkedList = new LinkedList<>(collection);
  private final HashSet<Integer> hashSet = new HashSet<>(collection);
  private final Stack<Integer> stack = new Stack<>();

  @Before
  public void setUp() {
    collection.forEach(i -> stack.push(i));
  }

  @Test
  public void testListAsInterface() {
    assertEquals(6, CollectionHelper.sumOfList(arrayList));
    assertEquals(6, CollectionHelper.sumOfList(linkedList));
  }

  @Test
  public void testCollectionAsInterface() {
    assertEquals(6, CollectionHelper.sumOfCollection(collection));
    assertEquals(6, CollectionHelper.sumOfCollection(arrayList));
    assertEquals(6, CollectionHelper.sumOfCollection(linkedList));
    assertEquals(6, CollectionHelper.sumOfCollection(hashSet));
    assertEquals(6, CollectionHelper.sumOfCollection(stack));
    assertEquals(3, stack.size());
  }

  @Test
  public void testIterableAsInterface() {
    assertEquals(6, CollectionHelper.sumOfIterable(collection));
    assertEquals(6, CollectionHelper.sumOfIterable(arrayList));
    assertEquals(6, CollectionHelper.sumOfIterable(linkedList));
    assertEquals(6, CollectionHelper.sumOfIterable(hashSet));
    assertEquals(6, CollectionHelper.sumOfIterable(stack));
  }
}
