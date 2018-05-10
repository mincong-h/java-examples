package io.mincong.ocajp.chapter3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class ArrayTest {

  private String[] words;

  @Before
  public void setUp() {
    words = new String[]{"a", "b", "c"};
  }

  @Test
  public void testCreateWithRef() {
    String[] arrayWithoutNew = {"a", "b", "c"};
    assertEquals(3, arrayWithoutNew.length);

    String[] alias = words;
    assertSame(words, alias);
  }

  @Test
  public void testArraysToString() {
    String[] words = {"a", "b", "c"};
    assertEquals("[a, b, c]", Arrays.toString(words));
  }

  @Test
  public void testArrayStoreException() {
    String[] strings = {"hello"};
    Object[] objects = strings;
    try {
      objects[0] = new StringBuilder();
      fail();
    } catch (ArrayStoreException e) {
      assertEquals(e.getMessage(), "java.lang.StringBuilder", e.getMessage());
    }
  }

  @Test
  public void testIterationUsingIndex() {
    for (int i = 0; i < words.length; i++) {
      assertEquals(String.valueOf((char) ('a' + i)), words[i]);
    }
    int i = 0;
    for (String word : words) {
      assertEquals(String.valueOf((char) ('a' + i)), word);
      i++;
    }
  }

  @Test
  public void testSorting() {
    // numeric order
    int[] intArr = {10, 3, 2};
    Arrays.sort(intArr);
    assertEquals(2, intArr[0]);
    assertEquals(3, intArr[1]);
    assertEquals(10, intArr[2]);

    // lexicographical order (alphabetical order)
    String[] strArr = {"10", "3", "2"};
    Arrays.sort(strArr);
    assertEquals("10", strArr[0]);
    assertEquals("2", strArr[1]);
    assertEquals("3", strArr[2]);
  }

  @Test
  public void testSearching() {
    int[] numbers = {2, 4, 6, 8};
    assertEquals(0, Arrays.binarySearch(numbers, 2));
    assertEquals(1, Arrays.binarySearch(numbers, 4));
    assertEquals(-1, Arrays.binarySearch(numbers, 1)); // -1 - 0
    assertEquals(-2, Arrays.binarySearch(numbers, 3)); // -1 - 1
    assertEquals(-5, Arrays.binarySearch(numbers, 9)); // -1 - 4
  }

  @Test
  public void testMultidimensionalArrays() {
    String[][] square1 = new String[][]{{"A1", "A2"}, {"B1", "B2"}};

    String[][] square2 = new String[2][2];
    square2[0][0] = "A1";
    square2[0][1] = "A2";
    square2[1][0] = "B1";
    square2[1][1] = "B2";

    for (int i = 0; i < square1.length; i++) {
      for (int j = 0; j < square1[i].length; j++) {
        assertTrue(square1[i][j].equals(square2[i][j]));
      }
    }

    StringBuilder builder = new StringBuilder(8);
    for (String[] row : square1) {
      for (String str : row) {
        builder.append(str);
      }
    }
    assertEquals("A1A2B1B2", builder.toString());
  }

}
