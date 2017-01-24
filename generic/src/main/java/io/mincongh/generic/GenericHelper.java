package io.mincongh.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericHelper {

  @SafeVarargs
  public static <E> List<E> toList(E... elements) {
    List<E> result = new ArrayList<>();
    for (E e : elements) {
      result.add(e);
    }
    return result;
  }

  public static <K, V> Map<K, V> buildMap(K key, V value) {
    Map<K, V> map = new HashMap<K, V>();
    map.put(key, value);
    return map;
  }

  public static <T> Class<?> getClass(T t) {
    return t.getClass();
  }
}
