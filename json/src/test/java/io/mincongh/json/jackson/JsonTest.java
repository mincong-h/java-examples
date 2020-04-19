package io.mincongh.json.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mincongh.json.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Data Binding converts JSON to and from POJOs based either on property accessor conventions or
 * annotations. There are two variants: simple and full data binding. Simple data binding means
 * converting to and from Java Maps, Lists, Strings, Numbers, Booleans and nulls. Full data binding
 * means converting to and from any Java bean type (as well as "simple" types mentioned above).
 * org.codehaus.jackson.map.ObjectMapper performs the marshalling (writing JSON) and unmarshalling
 * (reading JSON) for both variants. Inspired by the annotation-based (code-first) variant of JAXB.
 *
 * @author Mincong Huang
 * @see <a href="http://wiki.fasterxml.com/JacksonInFiveMinutes">Jackson in five minutes</a>
 */
class JsonTest {

  private static final String JSON_OBJ = "{\"name\":\"baby\",\"age\":3}";

  @Test
  void testFullDataBinding() throws Exception {
    JsonFactory jsonFactory = new JsonFactory();
    JsonParser jsonParser = jsonFactory.createParser(JSON_OBJ);
    ObjectMapper mapper = new ObjectMapper();

    assertEquals(JsonToken.START_OBJECT, jsonParser.nextToken());

    Person person = mapper.readValue(jsonParser, Person.class);
    assertEquals("baby", person.getName());
    assertEquals(3, person.getAge());
  }

  @Test
  void serializeObject() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String str = mapper.writeValueAsString(new MyExceptionDTO());
    assertThat(str).isEqualTo("{\"code\":1,\"msg\":\"hi\"}");
  }

  /*
   * Do not extend Exception or RuntimeException,
   * otherwise all the fields will be serialized.
   */
  @SuppressWarnings("unused")
  private static class MyExceptionDTO {
    @JsonProperty("code")
    private int errorCode = 1;

    @JsonProperty("msg")
    private String errorMessage = "hi";
  }
}
