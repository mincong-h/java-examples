package io.mincongh.xml.xpath;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Stack Overflow: how to parse xml to java in nodelist
 *
 * @author Mincong Huang
 */
class So50255561Test {

  private static final String XML =
      "<?xml version = \"1.0\" encoding = \"UTF-8\"?>\n"
          + "<ns0:GetADSLProfileResponse xmlns:ns0 = \"http://\">\n"
          + "  <ns0:Result>\n"
          + "    <ns0:eCode>0</ns0:eCode>\n"
          + "    <ns0:eDesc>Success</ns0:eDesc>\n"
          + "  </ns0:Result>\n"
          + "</ns0:GetADSLProfileResponse> ";

  private Document document;

  private XPath xPath;

  @BeforeEach
  void setUp() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    try (InputStream in = new ByteArrayInputStream(XML.getBytes(StandardCharsets.UTF_8))) {
      document = builder.parse(in);
    }
    xPath = XPathFactory.newInstance().newXPath();
  }

  @Test
  void xpath() throws Exception {
    XPathExpression expr =
        xPath.compile("/*[local-name()='GetADSLProfileResponse']/*[local-name()='Result']/*");

    NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    Function<Integer, String> f = i -> nodeList.item(i).getTextContent();
    assertThat(nodeList.getLength()).isEqualTo(2);
    assertThat(f.apply(0)).isEqualTo("0");
    assertThat(f.apply(1)).isEqualTo("Success");

    // demo
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      System.out.println(node.getNodeName() + ": " + node.getTextContent());
    }
  }
}
