package io.mincongh.json.jackson;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mincongh.json.Person;
import java.io.IOException;
import org.junit.Test;

/**
 * Data Binding converts JSON to and from POJOs based either on property accessor conventions or
 * annotations. There are two variants: simple and full data binding. Simple data binding means
 * converting to and from Java Maps, Lists, Strings, Numbers, Booleans and nulls. Full data binding
 * means converting to and from any Java bean type (as well as "simple" types mentioned above).
 * org.codehaus.jackson.map.ObjectMapper performs the marshalling (writing JSON) and unmarshalling
 * (reading JSON) for both variants. Inspired by the annotation-based (code-first) variant of JAXB.
 *
 * @author Mincong Huang
 * @see http://wiki.fasterxml.com/JacksonInFiveMinutes
 */
public class StreamingApiTest {

  private static final String JSON_OBJ = "{\"name\":\"baby\",\"age\":3}";

  @Test
  public void testFullDataBinding() throws IOException {
    JsonFactory jsonFactory = new JsonFactory();
    JsonParser jsonParser = jsonFactory.createParser(JSON_OBJ);
    ObjectMapper mapper = new ObjectMapper();

    assertEquals(JsonToken.START_OBJECT, jsonParser.nextToken());

    Person person = mapper.readValue(jsonParser, Person.class);
    assertEquals("baby", person.getName());
    assertEquals(3, person.getAge());
  }

  // TODO test simple data binding
}
