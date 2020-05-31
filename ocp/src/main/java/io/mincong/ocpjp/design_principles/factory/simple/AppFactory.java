package io.mincong.ocpjp.design_principles.factory.simple;

/** @author Mincong Huang */
public class AppFactory {

  public static App getAppInstance(String fileExt) {
    App app = null;
    if (".doc".equals(fileExt)) {
      app = new WordProcessor();
    } else if (".txt".equals(fileExt) || ".xml".equals(fileExt)) {
      app = new TextEditor();
    }
    return app;
  }
}
