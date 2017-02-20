package io.mincongh.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class HashMapTest {

  @Test
  public void testLongAsValue() {
    Map<String, Object> map = new HashMap();
    map.put("notNullLong", 1L);
    map.put("nullLong", null);
    assertEquals(1L, map.get("notNullLong"));
    assertNull(map.get("nullLong"));
  }

}
