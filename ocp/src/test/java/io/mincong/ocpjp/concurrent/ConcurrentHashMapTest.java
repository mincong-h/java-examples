package io.mincong.ocpjp.concurrent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.junit.Before;
import org.junit.Test;

/**
 * A concrete implementation of the {@link ConcurrentMap} interface, class {@link ConcurrentHashMap}
 * is a concurrent class analogous to class {@link java.util.HashMap}. A {@link java.util.HashMap}
 * is not synchronized. If you're manipulating a {@link java.util.HashMap} using multiple threads,
 * you must synchronized its access. But locking the entire HashMap object can create serious
 * performance issues when it's being accessed by multiple threads. If multiple threads are
 * retrieving values, it makes sense to allow concurrent read operations and monitor write
 * operations.
 *
 * @author Mincong Huang
 */
public class ConcurrentHashMapTest {

  private ConcurrentMap<String, String> map;

  @Before
  public void setUp() throws Exception {
    map = new ConcurrentHashMap<>();
    map.put("k1", "v1");
  }

  @Test
  public void putIfAbsent_nonexistentKey() throws Exception {
    map.putIfAbsent("k2", "v2");
    assertThat(map).containsOnly(entry("k1", "v1"), entry("k2", "v2"));
  }

  @Test
  public void putIfAbsent_existingKey() throws Exception {
    String val = map.putIfAbsent("k1", "v2");
    assertThat(val).isEqualTo("v1");
    assertThat(map).containsOnly(entry("k1", "v1"));
  }

  @Test
  public void replace_existingKey() throws Exception {
    String prev = map.replace("k1", "v2");
    assertThat(prev).isEqualTo("v1");
    assertThat(map).containsOnly(entry("k1", "v2"));
  }

  @Test
  public void replace_existingKey2() throws Exception {
    boolean isReplaced = map.replace("k1", "v1", "v2");
    assertThat(isReplaced).isTrue();
    assertThat(map).containsOnly(entry("k1", "v2"));
  }
}
