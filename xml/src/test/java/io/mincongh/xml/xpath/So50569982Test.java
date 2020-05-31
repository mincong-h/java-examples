package io.mincongh.xml.xpath;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.*;

/**
 * Stack Overflow: Reading XML file with java
 *
 * @author Mincong Huang
 */
class So50569982Test {

  private static final String XML =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
          + "<configuration>\n"
          + "    <metadonnees>\n"
          + "        <factory type=\"factory1\">\n"
          + "            <icones>\n"
          + "                <icone type=\"empty\" path=\"src/ressources/UMP0%.png\"/>\n"
          + "                <icone type=\"half\" path=\"src/ressources/UMP33%.png\"/>\n"
          + "                <icone type=\"full\" path=\"src/ressources/UMP100%.png\"/>\n"
          + "            </icones>\n"
          + "            <out type = \"materiel1\"/>\n"
          + "            <interval>100</interval>\n"
          + "        </factory>\n"
          + "        <factory type=\"factory2\">\n"
          + "            <icones>\n"
          + "                <icone type=\"empty\" path=\"src/ressources/UT0%.png\"/>\n"
          + "                <icone type=\"half\" path=\"src/ressources/UT33%.png\"/>\n"
          + "                <icone type=\"full\" path=\"src/ressources/UT100%.png\"/>\n"
          + "            </icones>\n"
          + "            <enter type=\"materiel1\" quantite=\"2\"/>\n"
          + "            <out type=\"materiel2\"/>\n"
          + "            <interval> 2 </interval>\n"
          + "        </factory>\n"
          + "\n"
          + "    </metadonnees>\n"
          + "\n"
          + "    <simulation>\n"
          + "        <factory type=\"factoty1\" id=\"11\" x=\"32\" y=\"32\"/>\n"
          + "        <factory type=\"factory2\" id=\"21\" x=\"320\" y=\"32\"/>\n"
          + "\n"
          + "        <paths>\n"
          + "            <path de=\"11\" vers=\"21\" />\n"
          + "            <path de=\"21\" vers=\"41\" />\n"
          + "            <path de=\"41\" vers=\"51\" />\n"
          + "            </paths>\n"
          + "    </simulation>\n"
          + "\n"
          + "</configuration>";

  private Document document;

  @BeforeEach
  void setUp() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try (InputStream in = new ByteArrayInputStream(XML.getBytes(StandardCharsets.UTF_8))) {
      document = factory.newDocumentBuilder().parse(in);
    }
  }

  @Test
  void node() throws Exception {
    XPath xPath = XPathFactory.newInstance().newXPath();
    XPathExpression expr = xPath.compile("//metadonnees/factory");
    NodeList usineList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    for (int i = 0; i < usineList.getLength(); i++) {
      Element usine = (Element) usineList.item(i);
      String typeUsine = usine.getAttribute("type");
      assertThat(typeUsine).startsWith("factory");
    }
  }

  @Test
  void attr() throws Exception {
    XPath xPath = XPathFactory.newInstance().newXPath();
    XPathExpression expr = xPath.compile("//metadonnees/factory/@type");
    NodeList typeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    for (int i = 0; i < typeList.getLength(); i++) {
      Attr type = (Attr) typeList.item(i);
      assertThat(type.getValue()).startsWith("factory");
    }
  }

  @Test
  void icons() throws Exception {
    XPath xPath = XPathFactory.newInstance().newXPath();
    NodeList usineList =
        (NodeList) xPath.evaluate("//metadonnees/factory", document, XPathConstants.NODESET);
    for (int i = 0; i < usineList.getLength(); i++) {
      Element usine = (Element) usineList.item(i);
      NodeList icons = (NodeList) xPath.evaluate(".//icone", usine, XPathConstants.NODESET);
      for (int j = 0; j < icons.getLength(); j++) {
        Element icon = (Element) icons.item(j);
        String type = icon.getAttribute("type");
        String path = icon.getAttribute("path");
        assertThat(type).isNotBlank();
        assertThat(path).isNotBlank();
      }
    }
  }
}
