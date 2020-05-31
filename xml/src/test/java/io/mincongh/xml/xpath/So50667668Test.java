package io.mincongh.xml.xpath;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Document;

/**
 * Stack Overflow: Undefined error while parsing java
 *
 * @author Mincong Huang
 */
public class So50667668Test {

  private static final String XML =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
          + "<books>\n"
          + "  <book id=\"1\"/>\n"
          + "  <book id=\"2\"/>\n"
          + "</books>\n";

  private Document document;

  @BeforeEach
  public void setUp() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try (InputStream in = new ByteArrayInputStream(XML.getBytes(StandardCharsets.UTF_8))) {
      document = factory.newDocumentBuilder().parse(in);
    }
  }

  @Test
  public void name(@TempDir Path root) throws Exception {
    File file = Files.createFile(root.resolve("books.xml")).toFile();
    try (Writer writer = new FileWriter(file)) {
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.transform(new DOMSource(document), new StreamResult(writer));
    }
    List<String> lines =
        Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).stream()
            .map(String::trim)
            .collect(Collectors.toList());
    assertThat(lines).contains("<books>", "<book id=\"1\"/>", "<book id=\"2\"/>", "</books>");
  }
}
