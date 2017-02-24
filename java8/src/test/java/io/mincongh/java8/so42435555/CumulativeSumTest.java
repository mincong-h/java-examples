package io.mincongh.java8.so42435555;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;

/**
 * JAVA - Perform a cumulative sum using forEach or stream API
 * https://stackoverflow.com/questions/42435555
 *
 * @author Mincong Huang
 */
public class CumulativeSumTest {

  @Test
  public void testCumulativeSumForMap() throws Exception {
    Map<String, Double> map = new HashMap<>();
    map.put("key1", 1.0);
    map.put("key2", 2.0);
    map.put("key3", 3.0);

    Double sum = map.values().stream().mapToDouble(Double::doubleValue).sum();
    assertEquals(6.0, sum, 0.000_000_1);
  }

  @Test
  public void testCumulativeSumForLinkedHashMap() throws Exception {
    Map<String, Double> map = new LinkedHashMap<>();
    map.put("first", 1.0);
    map.put("second", 5.0);
    map.put("third", 4.0);

    AtomicReference<Double> atomicSum = new AtomicReference<>(0.0);
    map.entrySet().forEach(e -> e.setValue(
        atomicSum.accumulateAndGet(e.getValue(), (x, y) -> x + y)
    ));
    assertEquals(10.0, atomicSum.get(), 0.0001);
    assertEquals(1.0, map.get("first"), 0.0001);
    assertEquals(6.0, map.get("second"), 0.0001);
    assertEquals(10.0, map.get("third"), 0.0001);
  }

}
