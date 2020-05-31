package io.mincongh.json.org_json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.mincongh.json.Person;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test JSON operation using <i>org.json:json</i> library.
 *
 * @author Mincong Huang
 */
class OrgJsonTest {

  private static final String JSON_OBJ_A = "{\"name\":\"personA\",\"age\":10}";
  private static final String JSON_OBJ_B = "{\"name\":\"personB\",\"age\":20}";
  private static final String JSON_OBJ_C = "{\"name\":\"personC\",\"age\":30}";
  private static final String JSON_ARRAY =
      String.format("[%s,%s,%s]", JSON_OBJ_A, JSON_OBJ_B, JSON_OBJ_C);

  private Person personA;
  private Person personB;
  private Person personC;
  private List<Person> people;

  @BeforeEach
  void setUp() {
    personA = new Person("personA", 10);
    personB = new Person("personB", 20);
    personC = new Person("personC", 30);
    people = Arrays.asList(personA, personB, personC);
  }

  @AfterEach
  void tearDown() {
    personA = null;
    personB = null;
    personC = null;
    people = null;
  }

  @Test
  void testConvertPojoToJsonObject() {
    JSONObject jsonObject = new JSONObject(personA);
    assertEquals(JSON_OBJ_A, jsonObject.toString());
  }

  @Test
  void testConvertArrayToJsonArray() {
    JSONArray jsonArray = new JSONArray(people);
    assertEquals(JSON_ARRAY, jsonArray.toString());
  }

  @Test
  void testConvertJsonObjectToPojo() {
    JSONObject jsonObject = new JSONObject(JSON_OBJ_A);
    assertEquals("personA", jsonObject.getString("name"));
    assertEquals(10, jsonObject.getInt("age"));
  }

  @Test
  void testConvertJsonArrayToPojo() {
    JSONArray jsonArray = new JSONArray(JSON_ARRAY);
    for (int i = 0; i < 3; i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      assertEquals("person" + (char) ('A' + i), jsonObject.getString("name"));
      assertEquals(i * 10 + 10, jsonObject.getInt("age"));
    }
  }

  @Test
  void testPutValueToJsonObject() {
    JSONObject jsonObject = new JSONObject(personA);
    jsonObject.put("key", "value");
    assertEquals("{\"name\":\"personA\",\"age\":10,\"key\":\"value\"}", jsonObject.toString());
  }

  @Test
  void testCreateJsonObjectUsingMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("key1", "value1");
    map.put("key2", 2);
    JSONObject jsonObject = new JSONObject(map);
    assertEquals("{\"key1\":\"value1\",\"key2\":2}", jsonObject.toString());
  }
}
