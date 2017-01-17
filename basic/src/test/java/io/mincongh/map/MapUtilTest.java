package io.mincongh.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class MapUtilTest {

  @Test
  public void testGetValueAsString() {
    Map<String, String> mapStrStr = new HashMap<>();
    mapStrStr.put("str", "value");
    mapStrStr.put("myKey", "myValue");
    assertTrue(mapStrStr instanceof Map);
    assertEquals("value", MapUtil.getValueAsString(mapStrStr, "str"));

    Map<String, Object> mapStrObj = new HashMap<>();
    mapStrObj.put("str", "value");
    mapStrObj.put("myKey", "myValue");
    mapStrObj.put("int", 123);
    mapStrObj.put("bool", true);
    assertTrue(mapStrStr instanceof Map);
    assertEquals("value", MapUtil.getValueAsString(mapStrObj, "str"));
    assertEquals("myValue", MapUtil.getValueAsString(mapStrObj, "myKey"));
    assertEquals("myValue", MapUtil.getMyKey(mapStrObj));
    assertEquals(123, MapUtil.getValueAsString(mapStrObj, "int"));
    assertEquals(true, MapUtil.getValueAsString(mapStrObj, "bool"));
  }
}
