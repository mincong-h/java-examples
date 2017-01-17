package io.mincongh.map;

import java.util.Map;

/**
 * @author Mincong Huang
 */
public class MapUtil {

  public static <K, V> V getValueAsString(Map<K, V> map, String key) {
    return map.get(key);
  }

  public static <K, V> V getMyKey(Map<K, V> map) {
    return map.get("myKey");
  }
}
