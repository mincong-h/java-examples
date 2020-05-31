package io.mincong.ocpjp.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;

/** @author Mincong Huang */
public class TreeMapTest {

  private TreeMap<Integer, String> m;

  @Before
  public void setUp() throws Exception {
    m = new TreeMap<>();
    m.put(1, "1");
    m.put(4, "4");
    m.put(2, "2");
  }

  @Test
  public void ceiling() throws Exception {
    assertThat(m.ceilingKey(3)).isEqualTo(4);
    assertThat(m.ceilingEntry(3)).isEqualTo(entry(4, "4"));
  }

  @Test
  public void floor() throws Exception {
    assertThat(m.floorKey(3)).isEqualTo(2);
    assertThat(m.floorEntry(3)).isEqualTo(entry(2, "2"));
  }

  @Test
  public void comparator() throws Exception {
    // No comparator specified, the natural order of K (Integer) is used.
    assertThat(m.comparator()).isNull();
  }

  @Test
  public void descending() throws Exception {
    assertThat(m.descendingKeySet()).containsExactly(4, 2, 1);
    assertThat(m.descendingMap()).containsOnly(entry(4, "4"), entry(2, "2"), entry(1, "1"));
  }
}
