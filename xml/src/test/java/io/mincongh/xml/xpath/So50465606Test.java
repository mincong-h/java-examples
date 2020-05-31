package io.mincongh.xml.xpath;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Stack Overflow: JAVA - Get “Header Description” from XML
 *
 * @author Mincong Huang
 */
class So50465606Test {

  private static final String XML =
      "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n"
          + "<Application>\n"
          + "\n"
          + "  <Header>\n"
          + "    <Version val=\"13010\"/>\n"
          + "    <WithIsn val=\"Y\"/>\n"
          + "    <_InternalTask val=\"N\"/>\n"
          + "    <IsRoot val=\"N\"/>\n"
          + "    <InIndex val=\"N\"/>\n"
          + "    <UniqueISNConvState val=\"10\"/>\n"
          + "  </Header>\n"
          + "\n"
          + "  <ProgramsRepository>\n"
          + "    <Programs>\n"
          + "      <Task MainProgram=\"N\">\n"
          + "        <Header Description=\"B.Opomene - Otprema_RTF\" ISN_2=\"1\" LastIsn=\"16\" id=\"827\">\n"
          + "          <Public val=\"B.PP436RTF\"/>\n"
          + "          <ExecutionRight comp=\"-1\"/>\n"
          + "          <Resident val=\"N\"/>\n"
          + "          <SQL val=\"N\"/>\n"
          + "        </Header>\n"
          + "      </Task>\n"
          + "    </Programs>\n"
          + "  </ProgramsRepository>\n"
          + "</Application>\n";

  @Test
  void xpath() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    Document document;
    try (InputStream in = new ByteArrayInputStream(XML.getBytes(StandardCharsets.UTF_8))) {
      document = factory.newDocumentBuilder().parse(in);
    }
    XPath xPath = XPathFactory.newInstance().newXPath();
    List<XPathExpression> expressions = new ArrayList<>(2);
    expressions.add(
        xPath.compile("/Application/ProgramsRepository/Programs/Task/Header/@Description"));
    expressions.add(xPath.compile("//Task/Header/@Description"));

    for (XPathExpression expr : expressions) {
      Node node = (Node) expr.evaluate(document, XPathConstants.NODE);
      assertThat(node.getTextContent()).isEqualTo("B.Opomene - Otprema_RTF");
    }
  }
}
