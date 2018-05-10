package io.mincong.ocpjp.design_principles.factory.abstract_;

/**
 * @author Mincong Huang
 */
public class TextEditAppFactory extends AppFactory {

  @Override
  public App getApp() {
    return new TextEditor();
  }

  @Override
  public Font getFont() {
    return new RegularFont();
  }

}
