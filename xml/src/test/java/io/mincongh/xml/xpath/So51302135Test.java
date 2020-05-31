package io.mincongh.xml.xpath;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.*;

/**
 * Stack Overflow: XML NodeList is not empty but its node is null
 *
 * @author Mincong Huang
 */
class So51302135Test {

  private static final String XML =
      "<?xml version = \"1.0\" encoding = \"UTF-8\"?>\n"
          + "<OpenDRIVE>\n"
          + "  <road>\n"
          + "    <planView>\n"
          + "      <geometry s=\"0.000e+00\">\n"
          + "        <line/>\n"
          + "      </geometry>\n"
          + "      <geometry s=\"1.000e+01\">\n"
          + "        <arc/>\n"
          + "      </geometry>\n"
          + "    </planView>\n"
          + "  </road>\n"
          + "</OpenDRIVE>\n";

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
    NodeList planViews = document.getElementsByTagName("planView");

    int count = 0;
    for (int i = 0; i < planViews.getLength(); i++) {
      Node planView = planViews.item(i);
      for (int j = 0; j < planView.getChildNodes().getLength(); j++) {
        Node n = planView.getChildNodes().item(j);
        if (n instanceof Element) {
          count++;
        }
      }
    }
    assertThat(count).isEqualTo(2);
  }
}
