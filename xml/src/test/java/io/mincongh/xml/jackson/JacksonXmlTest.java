package io.mincongh.xml.jackson;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.mincongh.xml.jackson.model.User;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class JacksonXmlTest {

  @Test
  public void jacksonXmlRootElement() throws Exception {
    // language=XML
    String xml = "<user id=\"1\"><name>foo</name></user>";
    XmlMapper mapper = new XmlMapper();
    User u = mapper.readValue(xml, User.class);
    assertThat(u.getName()).isEqualTo("foo");
  }

  @Test
  public void jacksonXmlPropertyElement() throws Exception {
    // language=XML
    String xml = "<user id=\"1\"><name>foo</name></user>";
    XmlMapper mapper = new XmlMapper();
    User u = mapper.readValue(xml, User.class);
    assertThat(u.getId()).isEqualTo(1);
  }

  @Test
  public void jacksonXmlElementWrapper() throws Exception {
    // language=XML
    String xml =
        "<user id=\"1\"><name>foo</name><cards><card>C1</card><card>C2</card></cards></user>";
    XmlMapper mapper = new XmlMapper();
    User u = mapper.readValue(xml, User.class);
    assertThat(u.getCards()).containsExactly("C1", "C2");
  }
}
