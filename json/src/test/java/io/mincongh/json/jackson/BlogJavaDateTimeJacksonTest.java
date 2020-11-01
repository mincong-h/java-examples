package io.mincongh.json.jackson;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Instant;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Mincong Huang
 * @blog https://mincong.io/2020/10/25/java-time/
 */
class BlogJavaDateTimeJacksonTest {

  static class ClassA {
    @JsonProperty("value")
    private final long value;

    ClassA(@JsonProperty("value") long value) {
      this.value = value;
    }

    @JsonIgnore
    public Instant instant() {
      return Instant.ofEpochSecond(value);
    }
  }

  static class ClassB {
    @JsonProperty("value")
    private final Instant value;

    ClassB(@JsonProperty("value") Instant value) {
      this.value = value;
    }
  }

  /** Use epoch second (timestamp) in JSON. */
  @Nested
  class TimestampLongTest {
    @Test
    void serialize() throws Exception {
      var objectMapper = new ObjectMapper();
      var obj = new ClassA(1601510400L);
      var json = objectMapper.writeValueAsString(obj);
      assertThat(json).isEqualTo("{\"value\":1601510400}");
    }

    @Test
    void deserialize() throws Exception {
      var objectMapper = new ObjectMapper();
      var obj = objectMapper.readValue("{\"value\":1601510400}", ClassA.class);
      assertThat(obj.value).isEqualTo(1601510400L);
      assertThat(obj.instant()).isEqualTo(Instant.ofEpochSecond(1601510400L));
    }
  }

  /** Use {@link Instant} (ISO-8601 string) in JSON. */
  @Nested
  class InstantStringTest {

    @Test
    void serialize() throws Exception {
      var objectMapper = new ObjectMapper();
      /*
       * Registry Java Time Module to serialize Java Time objects.
       * see https://github.com/FasterXML/jackson-modules-java8.
       */
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

      var obj = new ClassB(LocalDateTime.of(2020, 10, 1, 0, 0).toInstant(UTC));
      var json = objectMapper.writeValueAsString(obj);
      assertThat(json).isEqualTo("{\"value\":\"2020-10-01T00:00:00Z\"}");
    }

    @Test
    void deserialize() throws Exception {
      var objectMapper = new ObjectMapper();
      /*
       * Registry Java Time Module to serialize Java Time objects.
       * see https://github.com/FasterXML/jackson-modules-java8.
       */
      objectMapper.registerModule(new JavaTimeModule());

      var obj = objectMapper.readValue("{\"value\":\"2020-10-01T00:00:00Z\"}", ClassB.class);
      assertThat(obj.value).isEqualTo(LocalDateTime.of(2020, 10, 1, 0, 0).toInstant(UTC));
    }
  }
}
