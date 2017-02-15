package io.mincongh.concurrency;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mincong HUANG
 */
public class MyCounter {

  private Map<Integer, Integer> map = new HashMap<>();

  public synchronized void incrementKey(int key) {
    int prevValue = map.getOrDefault(key, 0);
    map.put(key, prevValue + 1);
  }

  public synchronized int get(int key) {
    return map.get(key);
  }

}
