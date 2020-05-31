package io.mincong.ocpjp.concurrent;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import org.junit.Test;

/** @author Mincong Huang */
public class ReviewNoteTest {

  /* Concurrent collection classes */

  @Test
  public void concurrentMap() throws Exception {
    ConcurrentMap<String, String> m = new ConcurrentHashMap<>(2);

    // Create
    m.put("k1", "v1");
    m.put("k2", "v2");
    m.putIfAbsent("k2", "k2 exists already");

    // Read
    assertThat(m.get("k1")).isEqualTo("v1");
    assertThat(m.get("k2")).isEqualTo("v2");

    // Update
    assertThat(m.replace("k1", "v1.1")).isEqualTo("v1");
    assertThat(m.replace("k2", "v2", "v2.1")).isTrue();

    m.replaceAll((k, v) -> m.replace(k, 'v' + k.substring(1)));
    assertThat(m.get("k1")).isEqualTo("v1");
    assertThat(m.get("k2")).isEqualTo("v2");

    // Delete
    assertThat(m.remove("k1")).isEqualTo("v1");
    assertThat(m.remove("k2", "v2.1")).isFalse();
    assertThat(m.remove("k2", "v2")).isTrue();
  }

  @Test
  public void concurrentNavigableMap() throws Exception {
    ConcurrentNavigableMap<Integer, String> m = new ConcurrentSkipListMap<>();
    m.put(3, "three");
    m.put(1, "one");
    m.put(6, "six");
    m.put(2, "two");

    // key
    assertThat(m.navigableKeySet()).containsExactly(1, 2, 3, 6);
    assertThat(m.descendingKeySet()).containsExactly(6, 3, 2, 1);

    assertThat(m.firstKey()).isEqualTo(1);
    assertThat(m.lastKey()).isEqualTo(6);

    assertThat(m.lowerKey(3)).isEqualTo(2);
    assertThat(m.higherKey(3)).isEqualTo(6);
    assertThat(m.floorKey(3)).isEqualTo(3);
    assertThat(m.floorKey(4)).isEqualTo(3);
    assertThat(m.ceilingKey(4)).isEqualTo(6);

    // sub maps
    assertThat(m.subMap(1, 3)).containsOnlyKeys(1, 2);
    assertThat(m.subMap(1, true, 3, false)).containsOnlyKeys(1, 2);
    assertThat(m.subMap(1, true, 3, true)).containsOnlyKeys(1, 2, 3);
    assertThat(m.subMap(1, false, 3, true)).containsOnlyKeys(2, 3);
    assertThat(m.subMap(1, false, 3, false)).containsOnlyKeys(2);

    assertThat(m.headMap(3)).containsOnlyKeys(1, 2);
    assertThat(m.headMap(3, true)).containsOnlyKeys(1, 2, 3);

    assertThat(m.tailMap(3)).containsOnlyKeys(3, 6);
    assertThat(m.tailMap(3, false)).containsOnlyKeys(6);
  }

  /* Locks */

  @Test
  public void atomicInteger() throws Exception {
    AtomicInteger i = new AtomicInteger(1);

    assertThat(i.incrementAndGet()).isEqualTo(2);
    assertThat(i.decrementAndGet()).isEqualTo(1);
    assertThat(i.accumulateAndGet(3, (prev, x) -> prev * x)).isEqualTo(3);
    assertThat(i.updateAndGet(Math::abs)).isEqualTo(3);

    assertThat(i.compareAndSet(3, 0)).isTrue();
    assertThat(i.compareAndSet(1, 0)).isFalse();
  }

  @Test
  public void atomicIntegerArray() throws Exception {
    AtomicIntegerArray array = new AtomicIntegerArray(new int[] {1, 2, 3, -4});

    assertThat(array.incrementAndGet(0)).isEqualTo(2);
    assertThat(array.decrementAndGet(1)).isEqualTo(1);
    assertThat(array.accumulateAndGet(2, 10, (prev, x) -> prev * x)).isEqualTo(30);
    assertThat(array.updateAndGet(3, Math::abs)).isEqualTo(4);

    assertThat(array.get(0)).isEqualTo(2);
    assertThat(array.get(1)).isEqualTo(1);
    assertThat(array.get(2)).isEqualTo(30);
    assertThat(array.get(3)).isEqualTo(4);
  }

  /* Executors */

  /* Parallel fork/join framework */

}
