package io.mincongh.xml.xpath;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * Stack Overflow: XML highest score
 *
 * @author Mincong Huang
 */
public class So51031488Test {

  private static final String XML =
      "<?xml version = \"1.0\" encoding = \"UTF-8\"?>\n"
          + "<root>\n"
          + "  <Q>\n"
          + "    <QID>1</QID>\n"
          + "    <Ans>\n"
          + "      <Score>1</Score>\n"
          + "      <Choice>Choice 1</Choice>\n"
          + "    </Ans>\n"
          + "    <Ans>\n"
          + "      <Score>2</Score>\n"
          + "      <Choice>Choice 2</Choice>\n"
          + "    </Ans>\n"
          + "    <Ans>\n"
          + "      <Score>3</Score>\n"
          + "      <Choice>Choice 3</Choice>\n"
          + "    </Ans>\n"
          + "  </Q>\n"
          + "  <Q>\n"
          + "    <QID>2</QID>\n"
          + "    <Ans>\n"
          + "      <Score>10</Score>\n"
          + "      <Choice>Choice A</Choice>\n"
          + "    </Ans>\n"
          + "    <Ans>\n"
          + "      <Score>20</Score>\n"
          + "      <Choice>Choice B</Choice>\n"
          + "    </Ans>\n"
          + "    <Ans>\n"
          + "      <Score>30</Score>\n"
          + "      <Choice>Choice C</Choice>\n"
          + "    </Ans>\n"
          + "  </Q>\n"
          + "</root>\n";

  private Document document;

  @Before
  public void setUp() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    try (InputStream in = new ByteArrayInputStream(XML.getBytes(StandardCharsets.UTF_8))) {
      document = builder.parse(in);
    }
  }

  @Test
  public void addAttrElement() throws Exception {
    Map<Integer, Double> scoreMap = new HashMap<>();
    XPath xpath = XPathFactory.newInstance().newXPath();
    NodeList questions = (NodeList) xpath.evaluate("/root/Q", document, XPathConstants.NODESET);
    for (int i = 0; i < questions.getLength(); i++) {
      Node question = questions.item(i);
      int questionId = Integer.parseInt(xpath.evaluate("QID", question));
      NodeList answers = (NodeList) xpath.evaluate("Ans/Score", question, XPathConstants.NODESET);
      double maxScore = 0;
      for (int j = 0; j < answers.getLength(); j++) {
        double score = Double.parseDouble(answers.item(i).getTextContent());
        maxScore = Math.max(score, maxScore);
      }
      scoreMap.put(questionId, maxScore);
    }
    assertThat(scoreMap).containsOnly(entry(1, 1d), entry(2, 20d));
  }
}
