package io.mincongh.xml.xpath;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Stack Overflow: Include delimiter when performing substring operation
 *
 * @author Mincong Huang
 */
public class So50352576Test {

  private static final String XML =
      "<?xml version = \"1.0\" encoding = \"UTF-8\"?>\n"
          + "<message>\n"
          + "  <food>\n"
          + "    <name>A</name>\n"
          + "  </food>\n"
          + "  <food>\n"
          + "    <name>B</name>\n"
          + "  </food>\n"
          + "</message>\n";

  @Test
  public void xpath() throws Exception {
    // Deserialize
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    Document document;
    try (InputStream in = new ByteArrayInputStream(XML.getBytes(StandardCharsets.UTF_8))) {
      document = factory.newDocumentBuilder().parse(in);
    }
    XPath xPath = XPathFactory.newInstance().newXPath();
    XPathExpression expr = xPath.compile("//food");

    NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    Function<Integer, String> f = i -> nodeList.item(i).getTextContent().trim();
    assertThat(nodeList.getLength()).isEqualTo(2);
    assertThat(f.apply(0)).isEqualTo("A");
    assertThat(f.apply(1)).isEqualTo("B");

    // demo
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      System.out.println(node.getNodeName() + ": " + node.getTextContent().trim());
    }

    // Serialize
    Document exportDoc = factory.newDocumentBuilder().newDocument();
    Node exportNode = exportDoc.importNode(nodeList.item(0), true);
    exportDoc.appendChild(exportNode);
    String content = serialize(exportDoc);
    System.out.println(content);
  }

  private static String serialize(Document doc) throws TransformerException {
    DOMSource domSource = new DOMSource(doc);
    StringWriter writer = new StringWriter();
    StreamResult result = new StreamResult(writer);
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    // set indent
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    transformer.transform(domSource, result);
    return writer.toString();
  }
}
