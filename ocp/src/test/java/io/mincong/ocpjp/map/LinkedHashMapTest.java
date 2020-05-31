package io.mincong.ocpjp.map;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Test;

/** @author Mincong Huang */
public class LinkedHashMapTest {

  /**
   * Different from its parent {@link java.util.HashMap}, the class {@link LinkedHashMap} has a
   * predictable iteration order. Like a {@link java.util.LinkedList}, a {@link LinkedHashMap}
   * maintains a double-linked list that runs through all its entries.
   */
  @Test
  public void fixedIterationOrder() throws Exception {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("A", "1");
    map.put("B", "2");
    map.put("C", "3");
    String result = map.values().stream().collect(Collectors.joining());
    assertThat(result).isEqualTo("123");
  }
}
