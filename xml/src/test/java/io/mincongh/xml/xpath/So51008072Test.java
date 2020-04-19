package io.mincongh.xml.xpath;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Stack Overflow: XML createElement double quotes DocumentBuilder api
 *
 * @author Mincong Huang
 */
class So51008072Test {

  private static final String XML =
      "<?xml version = \"1.0\" encoding = \"UTF-8\"?>\n"
          + "<PERSON>\n"
          + "   <AGE>30</AGE>\n"
          + "</PERSON>\n";

  private Document document;

  @BeforeEach
  void setUp() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    try (InputStream in = new ByteArrayInputStream(XML.getBytes(StandardCharsets.UTF_8))) {
      document = builder.parse(in);
    }
  }

  @Test
  void addAttrElement() {
    Element person = document.getDocumentElement();
    person.setAttribute("transactionType", "ADD");
    assertThat(person.getAttribute("transactionType")).isEqualTo("ADD");
  }
}
