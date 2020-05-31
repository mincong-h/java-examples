package io.mincongh.xml.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.mincongh.xml.jackson.model.User;
import org.junit.jupiter.api.Test;

/**
 * @author Mincong Huang
 * @since 1.0
 */
class JacksonXmlTest {

  @Test
  void jacksonXmlRootElement() throws Exception {
    // language=XML
    String xml = "<user id=\"1\"><name>foo</name></user>";
    XmlMapper mapper = new XmlMapper();
    User u = mapper.readValue(xml, User.class);
    assertThat(u.getName()).isEqualTo("foo");
  }

  @Test
  void jacksonXmlPropertyElement() throws Exception {
    // language=XML
    String xml = "<user id=\"1\"><name>foo</name></user>";
    XmlMapper mapper = new XmlMapper();
    User u = mapper.readValue(xml, User.class);
    assertThat(u.getId()).isEqualTo(1);
  }

  @Test
  void jacksonXmlElementWrapper() throws Exception {
    // language=XML
    String xml =
        "<user id=\"1\"><name>foo</name><cards><card>C1</card><card>C2</card></cards></user>";
    XmlMapper mapper = new XmlMapper();
    User u = mapper.readValue(xml, User.class);
    assertThat(u.getCards()).containsExactly("C1", "C2");
  }
}
