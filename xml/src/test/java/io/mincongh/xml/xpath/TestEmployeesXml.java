package io.mincongh.xml.xpath;

import static org.assertj.core.api.Assertions.assertThat;

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

/**
 * @author Mincong Huang
 * @see <a href="http://viralpatel.net/blogs/java-xml-xpath-tutorial-parse-xml/"> Java XPath
 * Tutorial: How to Parse XML File using XPath in Java</a>
 */
public class TestEmployeesXml {

  private static final String XML = "<?xml version=\"1.0\"?>\n"
      + "<employees>\n"
      + "  <employee id=\"1111\" type=\"admin\">\n"
      + "    <first-name>John</first-name>\n"
      + "    <last-name>Watson</last-name>\n"
      + "    <age>30</age>\n"
      + "    <email>johnwatson@sh.com</email>\n"
      + "  </employee>\n"
      + "  <employee id=\"2222\" type=\"admin\">\n"
      + "    <first-name>Sherlock</first-name>\n"
      + "    <last-name>Homes</last-name>\n"
      + "    <age>32</age>\n"
      + "    <email>sherlock@sh.com</email>\n"
      + "  </employee>\n"
      + "  <employee id=\"3333\" type=\"user\">\n"
      + "    <first-name>Jim</first-name>\n"
      + "    <last-name>Moriarty</last-name>\n"
      + "    <age>52</age>\n"
      + "    <email>jim@sh.com</email>\n"
      + "  </employee>\n"
      + "  <employee id=\"4444\" type=\"user\">\n"
      + "    <first-name>Mycroft</first-name>\n"
      + "    <last-name>Holmes</last-name>\n"
      + "    <age>41</age>\n"
      + "    <email>mycroft@sh.com</email>\n"
      + "  </employee>\n"
      + "</employees>\n";

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
  public void getFirstName() throws Exception {
    /*
     * `/employees/employee/first-name` selects all the `first-name`
     * elements in the same document as the context node that have an
     * `employee` parent.
     */
    XPathExpression expr = xPath.compile("/employees/employee/first-name");
    NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    Function<Integer, String> f = i -> nodeList.item(i).getTextContent();

    assertThat(nodeList.getLength()).isEqualTo(4);
    assertThat(f.apply(0)).isEqualTo("John");
    assertThat(f.apply(1)).isEqualTo("Sherlock");
    assertThat(f.apply(2)).isEqualTo("Jim");
    assertThat(f.apply(3)).isEqualTo("Mycroft");
  }

  @Test
  public void getEmployeeId() throws Exception {
    /*
     * `@id` selects the `id` attribute of the context node.
     */
    XPathExpression expr = xPath.compile("/employees/employee[@id='2222']");

    Node employee = (Node) expr.evaluate(document, XPathConstants.NODE);
    NodeList fields = employee.getChildNodes();
    Function<Integer, String> v = i -> fields.item(i).getTextContent();
    Function<Integer, Short> t = i -> fields.item(i).getNodeType();

    assertThat(fields.getLength()).isEqualTo(9);
    assertThat(t.apply(0)).isEqualTo(Node.TEXT_NODE);
    assertThat(v.apply(0)).isEqualTo("\n    ");
    assertThat(t.apply(1)).isEqualTo(Node.ELEMENT_NODE);
    assertThat(v.apply(1)).isEqualTo("Sherlock");
    assertThat(t.apply(2)).isEqualTo(Node.TEXT_NODE);
    assertThat(v.apply(2)).isEqualTo("\n    ");
    assertThat(t.apply(3)).isEqualTo(Node.ELEMENT_NODE);
    assertThat(v.apply(3)).isEqualTo("Homes");
    assertThat(t.apply(4)).isEqualTo(Node.TEXT_NODE);
    assertThat(v.apply(4)).isEqualTo("\n    ");
    assertThat(t.apply(5)).isEqualTo(Node.ELEMENT_NODE);
    assertThat(v.apply(5)).isEqualTo("32");
    assertThat(t.apply(6)).isEqualTo(Node.TEXT_NODE);
    assertThat(v.apply(6)).isEqualTo("\n    ");
    assertThat(t.apply(7)).isEqualTo(Node.ELEMENT_NODE);
    assertThat(v.apply(7)).isEqualTo("sherlock@sh.com");
    assertThat(t.apply(8)).isEqualTo(Node.TEXT_NODE);
    assertThat(v.apply(8)).isEqualTo("\n  ");
  }

}
