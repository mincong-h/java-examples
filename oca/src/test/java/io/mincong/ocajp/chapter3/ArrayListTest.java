package io.mincong.ocajp.chapter3;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

/** @author Mincong Huang */
public class ArrayListTest {

  /**
   * The {@code remove} methods remove the first mathcing value in the {@code ArrayList} or remove
   * the element at a specified index.
   */
  @Test
  public void testAddAndRemove() {
    List<String> words = new ArrayList<>();
    words.add("curr");
    words.add(1, "next");
    assertEquals("[curr, next]", words.toString());

    words.add(0, "prev");
    assertEquals("[prev, curr, next]", words.toString());

    words.add("curr");
    assertEquals("[prev, curr, next, curr]", words.toString());

    words.remove("curr");
    assertEquals("[prev, next, curr]", words.toString());
  }

  @Test
  public void testSet() {
    List<String> words = new ArrayList<>();
    words.add("a");
    words.add("b");
    words.add("c");
    words.set(1, "?");
    assertEquals("?", words.get(1));
  }

  @Test
  public void testWrapper() {
    int primitive = Integer.parseInt("123");
    Integer wrapper = Integer.valueOf("123");
    assertTrue(Integer.class.isInstance(primitive));
    assertTrue(Integer.class.isInstance(wrapper));

    try {
      Integer.parseInt("a");
      fail();
    } catch (NumberFormatException e) {
      assertEquals(e.getMessage(), "For input string: \"a\"", e.getMessage());
    }

    try {
      Integer.valueOf("123.45");
      fail();
    } catch (NumberFormatException e) {
      assertEquals(e.getMessage(), "For input string: \"123.45\"", e.getMessage());
    }
  }

  @Test
  public void testAutoboxing() {
    List<Double> weights = new ArrayList<>();
    weights.add(50.5);
    weights.add(new Double(60)); // ok

    weights.add(null);
    assertThrows(
        NullPointerException.class,
        () -> {
          @SuppressWarnings("unused")
          double w = weights.get(2); // cannot unwrap null
        });
  }

  @Test
  public void testConvertListToArray() {
    List<String> list = new ArrayList<>();
    list.add("hello");
    list.add("world");

    Object[] objectArray = list.toArray(); // default to Object
    String[] stringArray = list.toArray(new String[0]); // an String array with proper size

    assertSame("hello", objectArray[0]);
    assertSame("world", objectArray[1]);
    assertSame("hello", stringArray[0]);
    assertSame("world", stringArray[1]);
    assertEquals(2, objectArray.length);
    assertEquals(2, stringArray.length);
  }

  @Test
  public void testConvertArrayToList_usingArraysAsList() {
    String[] wordArray = {"one", "two"};
    List<String> wordList = Arrays.asList(wordArray);
    assertEquals("one", wordList.get(0));
    assertEquals("two", wordList.get(1));
    try {
      wordList.remove(1);
      fail();
    } catch (UnsupportedOperationException e) {
      assertNull(e.getMessage());
    }
    try {
      wordList.add("three");
      fail();
    } catch (UnsupportedOperationException e) {
      assertNull(e.getMessage());
    }
  }

  @Test
  public void testConvertArrayToList_usingNewArrayList() {
    String[] wordArray = {"one", "two"};
    List<String> wordList = new ArrayList<>();
    for (String word : wordArray) {
      wordList.add(word);
    }
    assertEquals("one", wordList.get(0));
    assertEquals("two", wordList.get(1));
    wordList.add("three");
    wordList.remove("three");
    assertEquals(2, wordList.size());
  }

  @Test
  public void testConvertArrayToList_usingStream() {
    String[] wordArray = {"one", "two"};
    List<String> wordList = Stream.of(wordArray).collect(Collectors.toList());
    assertEquals("one", wordList.get(0));
    assertEquals("two", wordList.get(1));
    wordList.add("three");
    wordList.remove("three");
    assertEquals(2, wordList.size());
  }

  @Test
  public void testSorting() {
    List<Integer> numbers = new ArrayList<>();
    numbers.add(3);
    numbers.add(10);
    numbers.add(6);

    Collections.sort(numbers);

    StringBuilder builder = new StringBuilder();
    for (int number : numbers) {
      builder.append(number).append("~");
    }
    assertEquals("3~6~10~", builder.toString());
  }
}
