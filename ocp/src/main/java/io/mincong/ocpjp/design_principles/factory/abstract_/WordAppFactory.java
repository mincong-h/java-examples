package io.mincong.ocpjp.design_principles.factory.abstract_;

/**
 * @author Mincong Huang
 */
public class WordAppFactory extends AppFactory {

  @Override
  public App getApp() {
    return new WordProcessor();
  }

  @Override
  public Font getFont() {
    return new RichFont();
  }
}
