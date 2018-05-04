package io.mincongh.xml.xpath;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Stack Overflow: Using XPATH inside node not getting the value, but does using document.
 *
 * @author Mincong Huang
 */
public class TestSo50174662 {

  private static final String XML =
      ""
          + "<book>\n"
          + "  <book1>\n"
          + "     <abc><fm>text</fm></abc>\n"
          + "     <def></def>\n"
          + "     <ghi></ghi>\n"
          + "  </book1>\n"
          + "  <book2>\n"
          + "     <abc><fm>text</fm></abc>\n"
          + "     <def></def>\n"
          + "     <ghi></ghi>\n"
          + "   </book2>\n"
          + "   <bookN>\n"
          + "     <abc><fm>text</fm></abc>\n"
          + "     <def></def>\n"
          + "     <ghi></ghi>\n"
          + "   </bookN>\n"
          + "</book>";

  private Document document;

  private XPath xPath;

  @Before
  public void setUp() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    try (InputStream in = new ByteArrayInputStream(XML.getBytes(StandardCharsets.UTF_8))) {
      document = builder.parse(in);
    }
    xPath = XPathFactory.newInstance().newXPath();
  }

  @Test
  public void inDocument() throws Exception {
    XPathExpression expr = xPath.compile("//fm/text()");

    // Solution 1: NodeSet
    NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    Function<Integer, String> f = i -> nodeList.item(i).getTextContent();
    assertThat(nodeList.getLength()).isEqualTo(3);
    assertThat(f.apply(0)).isEqualTo("text");
    assertThat(f.apply(1)).isEqualTo("text");
    assertThat(f.apply(2)).isEqualTo("text");

    // Solution 2: Node
    Node node = (Node) expr.evaluate(document, XPathConstants.NODE);
    assertThat(node.getTextContent()).isEqualTo("text");

    // Solution 3: String
    String string = (String) expr.evaluate(document, XPathConstants.STRING);
    assertThat(string).isEqualTo("text");
  }

  @Test
  public void inNode() throws Exception {
    Node book1 = (Node) xPath.compile("/book/book1").evaluate(document, XPathConstants.NODE);
    XPathExpression expr = xPath.compile(".//fm/text()"); // subtree

    // Solution 1: NodeSet
    NodeList nodeList = (NodeList) expr.evaluate(book1, XPathConstants.NODESET);
    assertThat(nodeList.getLength()).isEqualTo(1);
    assertThat(nodeList.item(0).getTextContent()).isEqualTo("text");

    // Solution 2: Node
    Node node = (Node) expr.evaluate(book1, XPathConstants.NODE);
    assertThat(node.getTextContent()).isEqualTo("text");

    // Solution 3: String
    String string = (String) expr.evaluate(book1, XPathConstants.STRING);
    assertThat(string).isEqualTo("text");
  }
}
