package io.mincong.ocpjp.design_principles.factory.abstract_;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/** @author Mincong Huang */
public class AbstractFactoryTest {

  @Test
  public void testAppFactory_doc() throws Exception {
    AppFactory factory = new WordAppFactory();
    App app = factory.getAppInstance();
    Font font = factory.getFontInstance();

    assertThat(app).isInstanceOf(WordProcessor.class);
    assertThat(font).isInstanceOf(RichFont.class);
  }

  @Test
  public void testAppFactory_xml() throws Exception {
    AppFactory factory = new TextEditAppFactory();
    App app = factory.getAppInstance();
    Font font = factory.getFontInstance();

    assertThat(app).isInstanceOf(TextEditor.class);
    assertThat(font).isInstanceOf(RegularFont.class);
  }
}
