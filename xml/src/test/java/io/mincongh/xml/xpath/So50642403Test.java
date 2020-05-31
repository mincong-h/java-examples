package io.mincongh.xml.xpath;

import static javax.xml.xpath.XPathConstants.NODE;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Stack Overflow: XPATH: Insert xml nodes into another XML document
 *
 * @author Mincong Huang
 */
class So50642403Test {

  private static final String XML1 =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
          + "<letterContent>\n"
          + "    <key1>key1</key1>\n"
          + "    <key2>key2</key2>\n"
          + "    <type>456</type>\n"
          + "    <object1>789</object1>\n"
          + "    <effectiveDate>00</effectiveDate>\n"
          + "    <expandedData attr1=\"case1\">\n"
          + "            <expandedData attr2=\"value2\">\n"
          + "                <data attrD=\"VD2\">value D2</data>\n"
          + "            </expandedData>\n"
          + "            <expandedData attr3=\"value3\">\n"
          + "                <data attrD=\"vd3\">value D3</data>\n"
          + "            </expandedData>\n"
          + "    </expandedData>\n"
          + "</letterContent>";
  private static final String XML2 =
      "<expandedData attr4=\"value4\">\n"
          + "    <data attrd4=\"vd4\">value d4</data>\n"
          + "    <name nameattr=\"specific_name\"/>\n"
          + "</expandedData>\n";

  private Document doc1;
  private Document doc2;

  @BeforeEach
  void setUp() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try (InputStream in = new ByteArrayInputStream(XML1.getBytes(StandardCharsets.UTF_8))) {
      doc1 = factory.newDocumentBuilder().parse(in);
    }
    try (InputStream in = new ByteArrayInputStream(XML2.getBytes(StandardCharsets.UTF_8))) {
      doc2 = factory.newDocumentBuilder().parse(in);
    }
  }

  @Test
  void name() throws Exception {
    XPath xpath = XPathFactory.newInstance().newXPath();
    Node e1 = (Node) xpath.evaluate("//expandedData[@attr1='case1']", doc1, NODE);
    Node e2 = (Node) xpath.evaluate("//expandedData[@attr4='value4']", doc2, NODE);
    e1.appendChild(doc1.importNode(e2, true));

    StringWriter writer = new StringWriter();
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.transform(new DOMSource(doc1), new StreamResult(writer));
    String content = writer.toString();
    assertThat(content).contains("<data attrd4=\"vd4\">value d4</data>");
    assertThat(content).contains("<name nameattr=\"specific_name\"/>");
  }
}
