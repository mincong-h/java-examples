package io.mincongh.xml.comparing;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests XML comparison.
 *
 * @author Mincong Huang
 */
public class XmlComparisonTest {

  /**
   * Definition of document type "users".
   *
   * <p>This document type declares 2 types of element: "users" and "user". Element "users" can be
   * nested by element "user" (at least 1 element). The content of element "user" is Parsed
   * Character Data (PCDATA).
   */
  private static final String DOCTYPE =
      "<!DOCTYPE users [ <!ELEMENT users (user+)> <!ELEMENT user (#PCDATA)> ]>";

  private DocumentBuilderFactory factory;

  @Before
  public void setUp() {
    factory = DocumentBuilderFactory.newInstance();
    factory.setIgnoringComments(true);
    /*
     * Ignores the content whitespace in element. It requires the xml
     * DOCTYPE to be declared, so that factory can distinguish PCDATA
     * and content whitespace. Line Feed '\n', tab '\t', and space
     * ' ' are whitespace.
     */
    factory.setIgnoringElementContentWhitespace(true);
  }

  @Test
  public void compareXml() {
    String content1 = DOCTYPE + "<users><user>A</user><user>B</user></users>";
    String content2 = DOCTYPE + "<users><user>A</user><user>B</user></users><!-- comment -->";
    String content3 = DOCTYPE + "<users><user>A</user><user>B</user>\n \t</users>";
    String content4 = DOCTYPE + "<users><user>A</user><user>B </user></users>";

    Document doc1 = parse(content1);
    Document doc2 = parse(content2);
    Document doc3 = parse(content3);
    Document doc4 = parse(content4);

    assertThat(doc1.isEqualNode(doc2)).isTrue();
    assertThat(doc1.isEqualNode(doc3)).isTrue();
    assertThat(doc1.isEqualNode(doc4)).isFalse();
  }

  private Document parse(String content) {
    try (InputStream in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(in);
      doc.normalizeDocument();
      return doc;
    } catch (ParserConfigurationException | IOException | SAXException e) {
      throw new IllegalStateException(e);
    }
  }
}
