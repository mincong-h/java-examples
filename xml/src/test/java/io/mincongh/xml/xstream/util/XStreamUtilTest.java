package io.mincongh.xml.xstream.util;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import io.mincongh.xml.xstream.model.Person;
import io.mincongh.xml.xstream.model.PhoneNumber;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class XStreamUtilTest {

  private Person p;

  private static String CONTENT_WITH_ALIAS;

  private static String CONTENT_WITHOUT_ALIAS;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    CONTENT_WITH_ALIAS = load("person-with-alias.xml");
    CONTENT_WITHOUT_ALIAS = load("person-without-alias.xml");
  }

  private static String load(String path) throws IOException {
    String p = XStreamUtilTest.class.getClassLoader().getResource(path).getPath();
    return String.join(System.lineSeparator(), Files.readAllLines(Paths.get(p)));
  }

  @Before
  public void setUp() throws Exception {
    p = new Person();
    p.setFirstName("Foo");
    p.setLastName("Bar");
    p.setAge(10);
    p.setPhoneNumber(new PhoneNumber(86, "123456"));
  }

  @Test
  public void serialize_withAnnotationProcessed() throws Exception {
    String result = XStreamUtil.serialize(p);
    assertThat(result).isEqualTo(CONTENT_WITH_ALIAS);
  }

  @Test
  public void serialize_withoutAnnotationProcessed() throws Exception {
    XStream xStream = new XStream(new DomDriver());
    String result = xStream.toXML(p);
    assertThat(result).isEqualTo(CONTENT_WITHOUT_ALIAS);
  }

  @Test
  public void deserialize() throws Exception {
    String xml = CONTENT_WITH_ALIAS;
    Person result = XStreamUtil.deserialize(xml, Person.class);
    assertThat(result).isEqualTo(p);
  }

}
