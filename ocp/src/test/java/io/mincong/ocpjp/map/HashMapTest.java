package io.mincong.ocpjp.map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class HashMapTest {

  private Map<String, Integer> defaultMap = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    defaultMap.put("A", 1);
    defaultMap.put("B", 2);
    defaultMap.put("C", 3);
  }

  @After
  public void tearDown() throws Exception {
    defaultMap.clear();
  }

  @Test
  public void newMapWithoutArgument() throws Exception {
    Map<String, Integer> map = defaultMap;
    assertThat(map).containsExactly(entry("A", 1), entry("B", 2), entry("C", 3));
  }

  @Test
  public void newMapCopiedFromExistingMap() throws Exception {
    Map<String, Integer> oldMap = defaultMap;
    Map<String, Integer> newMap = new HashMap<>(oldMap);
    assertThat(oldMap).containsExactly(entry("A", 1), entry("B", 2), entry("C", 3));
    assertThat(newMap).containsExactly(entry("A", 1), entry("B", 2), entry("C", 3));

    newMap.remove("C");
    assertThat(oldMap).containsExactly(entry("A", 1), entry("B", 2), entry("C", 3));
    assertThat(newMap).containsExactly(entry("A", 1), entry("B", 2));
  }

  @Test
  public void noDuplicate() throws Exception {
    defaultMap.put("A", 100);
    /*
     * If you add a key-value pair to a `HashMap` such that key
     * already exists in the `HashMap`, the key's old value will be
     * replaced with the new value.
     */
    assertThat(defaultMap).containsExactly(entry("A", 100), entry("B", 2), entry("C", 3));
  }

  @Test
  public void atMostOneNullKey() throws Exception {
    defaultMap.put(null, -1);
    assertThat(defaultMap.get(null)).isEqualTo(-1);

    defaultMap.put(null, -2);
    assertThat(defaultMap.get(null)).isEqualTo(-2);
  }

  @Test
  public void entrySize() throws Exception {
    assertThat(defaultMap.size()).isEqualTo(3);
  }

  @Test
  public void copyingAnotherMap() throws Exception {
    Map<String, Integer> map = defaultMap;
    Map<String, Integer> anotherMap = new HashMap<>();
    anotherMap.put("C", -3);
    anotherMap.put("D", -4);

    map.putAll(anotherMap);
    assertThat(map).containsExactly(entry("A", 1), entry("B", 2), entry("C", -3), entry("D", -4));
    assertThat(map).doesNotContain(entry("C", 3)); // Replaced by pair `entry("C", -3)`
  }

}
