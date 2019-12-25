package io.mincongh.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonFormatTest {

  static class JavaUtilDateExamples {
    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy", timezone = "UTC")
    @JsonProperty("fr")
    Date frenchDate = new Date(1577232000000L);
  }

  @Test
  public void formatJavaUtilDate() throws Exception {
    var examples = new JavaUtilDateExamples();
    var mapper =new ObjectMapper();
    assertThat(mapper.writeValueAsString(examples)).isEqualTo("{\"fr\":\"25/12/2019\"}");
    var examples2 = mapper.readValue("{\"fr\":\"25/12/2019\"}", JavaUtilDateExamples.class);
    assertThat(examples2.frenchDate).hasTime(1577232000000L);
  }
}
