package io.mincong.ocpjp.design_principles.factory.method;

/** @author Mincong Huang */
public class WordAppFactory extends AppFactory {

  @Override
  public App getApp() {
    return new WordProcessor();
  }
}
