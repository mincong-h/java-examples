package io.mincong.ocpjp.design_principles.factory.simple;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/** @author Mincong Huang */
public class AppFactoryTest {

  @Test
  public void testGetAppInstance_doc() throws Exception {
    App docApp = AppFactory.getAppInstance(".doc");
    String str = docApp.getResult("hello.doc");
    assertThat(str).isEqualTo("Launch WordProcessor using hello.doc");
  }

  @Test
  public void testGetAppInstance_xml() throws Exception {
    App xmlApp = AppFactory.getAppInstance(".xml");
    String str = xmlApp.getResult("hello.xml");
    assertThat(str).isEqualTo("Launch TextEditor using hello.xml");
  }
}
