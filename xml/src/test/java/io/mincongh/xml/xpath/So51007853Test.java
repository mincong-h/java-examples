package io.mincongh.xml.xpath;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Document;

/**
 * Stack Overflow: How to parse XML object in JAVA [duplicate]
 *
 * @author Mincong Huang
 */
public class So51007853Test {

  private File xmlFile;

  @BeforeEach
  public void setUp(@TempDir Path root) throws Exception {
    xmlFile = Files.createFile(root.resolve("my.xml")).toFile();
    String[] lines = {
      "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>",
      "<TccSubscriptionData xmlns=\"tcc.generated.interfaces.com\">",
      "    <MessageKey>",
      "        <MessageKey>12</MessageKey>",
      "        <Receiver>sc</Receiver>",
      "        <Timestamp>2018-06-20T14:33:22.968+02:00</Timestamp>",
      "        <ResponseType>Subscription</ResponseType>",
      "        <CorrelationId>0</CorrelationId>",
      "    </MessageKey>",
      "</TccSubscriptionData>"
    };
    Files.write(xmlFile.toPath(), Arrays.asList(lines));
  }

  @Test
  public void addAttrElement() throws Exception {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    try (InputStream in = new FileInputStream(xmlFile)) {
      Document doc = dBuilder.parse(in);
      doc.getDocumentElement().normalize();
      assertThat(doc.getElementsByTagName("Receiver").item(0).getTextContent()).isEqualTo("sc");
    }
  }
}
