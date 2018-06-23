package io.mincongh.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mincong Huang
 */
public class GenericHelper {

  private GenericHelper() {
    // Utility class, do not instantiate
  }

  @SafeVarargs
  public static <E> List<E> toList(E... elements) {
    List<E> result = new ArrayList<>();
    result.addAll(Arrays.asList(elements));
    return result;
  }

  public static <K, V> Map<K, V> buildMap(K key, V value) {
    Map<K, V> map = new HashMap<>();
    map.put(key, value);
    return map;
  }

  public static <T> Class<?> getClass(T t) {
    return t.getClass();
  }
}
