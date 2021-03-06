package io.mincongh.xml.xpath;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Stack Overflow: Accept xml message in java from server
 *
 * @author Mincong Huang
 */
@Disabled("Only for demo")
public class So50679068Test {

  @Test
  public void xml() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    URL url = new URL("https://raw.githubusercontent.com/mincong-h/java-examples/master/pom.xml");
    try (InputStream stream = url.openStream()) {
      Document doc = factory.newDocumentBuilder().parse(stream);

      XPath xpath = XPathFactory.newInstance().newXPath();
      NodeList modules = (NodeList) xpath.evaluate("//modules/module", doc, XPathConstants.NODESET);
      for (int i = 0; i < modules.getLength(); i++) {
        Node module = modules.item(i);
        System.out.println(module.getTextContent().trim());
      }
    }
  }

  @Test
  public void file(@TempDir Path root) throws Exception {
    URL url = new URL("https://raw.githubusercontent.com/mincong-h/java-examples/master/pom.xml");
    Path xml = root.resolve("pom.xml");
    try (InputStream stream = url.openStream()) {
      Files.copy(stream, xml);
    }
    Files.readAllLines(xml).forEach(System.out::println);
  }
}
