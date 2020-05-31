package io.mincong.ocpjp.design_principles.factory.abstract_;

/** @author Mincong Huang */
public abstract class AppFactory {

  public App getAppInstance() {
    return getApp();
  }

  public Font getFontInstance() {
    return getFont();
  }

  public abstract App getApp();

  public abstract Font getFont();
}
