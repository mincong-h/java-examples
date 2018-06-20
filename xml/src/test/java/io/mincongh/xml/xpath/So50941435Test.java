package io.mincongh.xml.xpath;

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
import org.w3c.dom.NodeList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Stack Overflow: how to parse xml to java in nodelist
 *
 * @author Mincong Huang
 */
public class So50941435Test {

  private static final String XML =
      "<?xml version = \"1.0\" encoding = \"UTF-8\"?>\n"
          + "<ElectricalProject>\n"
          + "  <Equipments>\n"
          + "    <Equipment>\n"
          + "      <Extensions>\n"
          + "        <Extension>\n"
          + "          <nfh:extensionProperty name=\"switchboardId\">switchboardid1</nfh:extensionProperty>\n"
          + "        </Extension>\n"
          + "      </Extensions>\n"
          + "    </Equipment>\n"
          + "  </Equipments>\n"
          + "</ElectricalProject>\n";

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
  public void xpath() throws Exception {
    XPathExpression expr =
        xPath.compile(
            "//ElectricalProject/Equipments/Equipment/Extensions/Extension/nfh:extensionProperty[@name='switchboardId']");
    NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    assertThat(nodeList.getLength()).isEqualTo(0);

    expr =
        xPath.compile(
            "//ElectricalProject/Equipments/Equipment/Extensions/Extension/*[local-name()='extensionProperty' and @name='switchboardId']");
    nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    assertThat(nodeList.getLength()).isEqualTo(1);
    assertThat(nodeList.item(0).getTextContent()).isEqualTo("switchboardid1");
  }
}
