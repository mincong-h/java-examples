package io.mincongh.xml.xstream.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import io.mincongh.xml.xstream.model.Person;
import io.mincongh.xml.xstream.model.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
class XStreamUtilTest {

  private static final String CONTENT_WITH_ALIAS =
      "<person>\n"
          + "  <firstName>Foo</firstName>\n"
          + "  <lastName>Bar</lastName>\n"
          + "  <age>10</age>\n"
          + "  <phoneNumber>\n"
          + "    <code>86</code>\n"
          + "    <number>123456</number>\n"
          + "  </phoneNumber>\n"
          + "</person>";

  private static final String CONTENT_WITHOUT_ALIAS =
      "<io.mincongh.xml.xstream.model.Person>\n"
          + "  <firstName>Foo</firstName>\n"
          + "  <lastName>Bar</lastName>\n"
          + "  <age>10</age>\n"
          + "  <phoneNumber>\n"
          + "    <code>86</code>\n"
          + "    <number>123456</number>\n"
          + "  </phoneNumber>\n"
          + "</io.mincongh.xml.xstream.model.Person>";

  private Person p;

  @BeforeEach
  void setUp() {
    p = new Person();
    p.setFirstName("Foo");
    p.setLastName("Bar");
    p.setAge(10);
    p.setPhoneNumber(new PhoneNumber(86, "123456"));
  }

  @Test
  void serialize_withAnnotationProcessed() {
    String result = XStreamUtil.serialize(p);
    assertThat(result).isEqualTo(CONTENT_WITH_ALIAS);
  }

  @Test
  void serialize_withoutAnnotationProcessed() {
    XStream xStream = new XStream(new DomDriver());
    String result = xStream.toXML(p);
    assertThat(result).isEqualTo(CONTENT_WITHOUT_ALIAS);
  }

  @Test
  void deserialize() {
    Person result = XStreamUtil.deserialize(CONTENT_WITH_ALIAS, Person.class);
    assertThat(result).isEqualTo(p);
  }
}
