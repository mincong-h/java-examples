package io.mincongh.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>
 * {@code @ManagedBean} marks a bean to be a managed bean with the name specified in name attribute.
 * If the name attribute is not specified, then the managed bean name will default to class name
 * portion of the fully qualified class name. In our case it would be {@code massageBox}.
 *
 * <p>
 * Scope Annotations set the scope into which the managed bean will be placed. If scope is not
 * specified then bean will default to request scope. Each scope is briefly discussed below
 *
 * @author Mincong Huang
 */
@ManagedBean(name = "messageBox", eager = true)
@SessionScoped
public class MessageBox {

  private static final Logger LOGGER = LogManager.getLogger(MessageBox.class);

  private String name = "Hello JSF";

  public MessageBox() {
    LOGGER.info(MessageBox.class.getSimpleName() + " started");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
