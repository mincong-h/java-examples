package io.mincong.ocpjp.design_principles.factory.method;

/** @author Mincong Huang */
public abstract class AppFactory {

  public App getAppInstance() {
    return getApp();
  }

  public abstract App getApp();
}
