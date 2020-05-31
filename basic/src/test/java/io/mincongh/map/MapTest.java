package io.mincongh.map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import org.junit.jupiter.api.Test;

class MapTest {

  @Test
  void testLongAsValue() {
    Map<String, Object> map = new HashMap<>();
    map.put("notNullLong", 1L);
    map.put("nullLong", null);
    assertEquals(1L, map.get("notNullLong"));
    assertNull(map.get("nullLong"));
  }

  @Test
  void so50134298() {
    Map<String, BigDecimal> dataSet = new HashMap<>();
    dataSet.put("A", new BigDecimal(12));
    dataSet.put("B", new BigDecimal(23));
    dataSet.put("C", new BigDecimal(67));
    dataSet.put("D", new BigDecimal(99));

    // Map(k=MinValue, v=Count)
    NavigableMap<BigDecimal, Integer> partitions = new TreeMap<>();
    partitions.put(new BigDecimal(0), 0);
    partitions.put(new BigDecimal(25), 0);
    partitions.put(new BigDecimal(50), 0);
    partitions.put(new BigDecimal(75), 0);
    partitions.put(new BigDecimal(100), 0);

    for (BigDecimal d : dataSet.values()) {
      Entry<BigDecimal, Integer> e = partitions.floorEntry(d);
      partitions.put(e.getKey(), e.getValue() + 1);
    }

    assertThat(partitions)
        .containsEntry(new BigDecimal(0), 2)
        .containsEntry(new BigDecimal(25), 0)
        .containsEntry(new BigDecimal(50), 1)
        .containsEntry(new BigDecimal(75), 1)
        .containsEntry(new BigDecimal(100), 0);
  }
}
