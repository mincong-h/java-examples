package io.mincongh.xml.jackson.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

/**
 * @author Mincong Huang
 * @since 1.0
 */
@JacksonXmlRootElement(localName = "user")
public class User {
  @JacksonXmlProperty(isAttribute = true)
  private int id;

  @JacksonXmlProperty //
  private String name;

  @JacksonXmlElementWrapper(localName = "cards")
  @JacksonXmlProperty(localName = "card")
  private List<String> cards;

  public User() {}

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<String> getCards() {
    return cards;
  }
}
