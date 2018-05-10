package io.mincong.ocpjp.generic;

/**
 * @param <K> Key
 * @param <V> Value
 * @author Mincong Huang
 */
public interface MyMap<K, V> {

  /**
   * Operation "put" accepts a key of type K and a value of type V.
   */
  void put(K key, V value);

  /**
   * For a key of type K, operation "get" returns a value of type V.
   */
  V get(K key);
}
