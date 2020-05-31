package io.mincong.ocpjp.design_principles.factory.method;

/** @author Mincong Huang */
public class WordProcessor implements App {

  @Override
  public String getResult(String fileName) {
    String className = getClass().getSimpleName();
    return "Launch " + className + " using " + fileName;
  }
}
