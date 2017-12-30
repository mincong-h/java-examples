package io.mincongh.xml.xpath;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Use XPath to query HTML file.
 *
 * @author Mincong Huang
 */
public class TestMultipleNodes {

  private static final String HTML = "<div>\n"
      + "  <div id=\"target\">\n"
      + "    <div>\n"
      + "      <div>\n"
      + "        <div>\n"
      + "          <div class=\"btn white\">A</div>\n"
      + "        </div>\n"
      + "        <div>\n"
      + "          <div class=\"btn black\">B</div>\n"
      + "        </div>\n"
      + "        <div>\n"
      + "          <div class=\"btn\">C</div>\n"
      + "        </div>\n"
      + "      </div>\n"
      + "    </div>\n"
      + "  </div>\n"
      + "</div>\n";

  private Document document;

  private XPath xPath;

  @Before
  public void setUp() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    try (InputStream in = new ByteArrayInputStream(HTML.getBytes(StandardCharsets.UTF_8))) {
      document = builder.parse(in);
    }
    xPath = XPathFactory.newInstance().newXPath();
  }

  @Test
  public void exactMatch() throws Exception {
    XPathExpression expr = xPath.compile("//div[@id='target']//div[@class='btn']");
    NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

    assertThat(nodes.getLength()).isEqualTo(1);
    assertThat(nodes.item(0).getNodeType()).isEqualTo(Node.ELEMENT_NODE);
    assertThat(nodes.item(0).getTextContent()).isEqualTo("C");
  }

  /**
   * Match on an attribute that contains a certain string.
   *
   * @see <a href="https://stackoverflow.com/questions/1390568">Stack Overflow</a>
   */
  @Test
  public void contains() throws Exception {
    XPathExpression expr = xPath.compile("//div[@id='target']//div[contains(@class, 'btn')]");
    NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

    assertThat(nodes.getLength()).isEqualTo(3);
    assertThat(nodes.item(0).getTextContent()).isEqualTo("A");
    assertThat(nodes.item(1).getTextContent()).isEqualTo("B");
    assertThat(nodes.item(2).getTextContent()).isEqualTo("C");
  }

}
