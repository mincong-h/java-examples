package io.mincong.ocpjp.design_principles.factory.method;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/** @author Mincong Huang */
public class MultiAppFactoryTest {

  @Test
  public void testAppFactory_doc() throws Exception {
    AppFactory factory = new WordAppFactory();
    App app = factory.getAppInstance();
    String str = app.getResult("Hello.doc");

    assertThat(str).isEqualTo("Launch WordProcessor using Hello.doc");
  }

  @Test
  public void testAppFactory_xml() throws Exception {
    AppFactory factory = new TextEditAppFactory();
    App app = factory.getAppInstance();
    String str = app.getResult("Hello.xml");

    assertThat(str).isEqualTo("Launch TextEditor using Hello.xml");
  }
}
