package io.mincong.ocpjp.map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * <p>
 * <b>Key comparing.</b> Unlike a {@link java.util.HashMap}, a
 * {@link TreeMap} uses method {@link Comparable#compareTo(Object)}
 * or method {@link Comparator#compare(Object, Object)} to determine
 * the equality of its keys. So, it can access the value associated
 * to a key without using the {@link Object#equals(Object)} and
 * {@link Object#hashCode()} of its elements.
 *
 * @author Mincong Huang
 */
public class TreeMapTest {

  /**
   * If the key of {@link TreeMap} are {@link java.lang.Comparable},
   * then {@link TreeMap} can use the key's natural order as order of
   * the map.
   */
  @Test
  public void naturalOrder() throws Exception {
    Map<String, String> treeMap = new TreeMap<>();
    treeMap.put("C", "3");
    treeMap.put("B", "2");
    treeMap.put("A", "1");

    String result = treeMap.values().stream().collect(Collectors.joining());
    assertThat(result).isEqualTo("123");
  }

  @Test
  public void noOrder() throws Exception {
    Map<NotComparable, String> treeMap = new TreeMap<>();
    try {
      treeMap.put(new NotComparable("A"), "1");
      fail("NotComparable should not be able to be put into a tree map: it is not comparable.");
    } catch (ClassCastException e) {
      // Ok
    }
  }

  @Test
  public void comparatorOrder() throws Exception {
    Map<NotComparable, String> treeMap = new TreeMap<>(Comparator.comparing(NotComparable::getS));
    treeMap.put(new NotComparable("C"), "3");
    treeMap.put(new NotComparable("B"), "2");
    treeMap.put(new NotComparable("A"), "1");

    String result = treeMap.values().stream().collect(Collectors.joining());
    assertThat(result).isEqualTo("123");
  }

  private static class NotComparable {

    private String s;

    NotComparable(String s) {
      this.s = s;
    }

    String getS() {
      return s;
    }
  }

}
