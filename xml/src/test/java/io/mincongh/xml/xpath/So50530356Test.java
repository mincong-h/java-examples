package io.mincongh.xml.xpath;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * StackOverflow: XQL select following element
 *
 * @author Mincong Huang
 * @see <a href="https://stackoverflow.com/questions/50530356/xql-select-following-element">XQL
 *     select following element</a>
 */
class So50530356Test {

  private static final String XML =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
          + "<users>\n"
          + "  <user id=\"A\"/>\n"
          + "  <user id=\"B\"/>\n"
          + "  <user id=\"C\"/>\n"
          + "</users>\n";

  @Test
  void xpath() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    Document document;
    try (InputStream in = new ByteArrayInputStream(XML.getBytes(StandardCharsets.UTF_8))) {
      document = factory.newDocumentBuilder().parse(in);
    }
    XPath xPath = XPathFactory.newInstance().newXPath();
    XPathExpression expr = xPath.compile("//user[@id='B']/following-sibling::user[1]/@id");

    Node node = (Node) expr.evaluate(document, XPathConstants.NODE);
    assertThat(node.getTextContent()).isEqualTo("C");
  }
}
