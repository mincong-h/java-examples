package io.mincong.ocpjp.design_principles.factory.simple;

/** @author Mincong Huang */
public class WordProcessor implements App {

  @Override
  public String getResult(String fileName) {
    String className = WordProcessor.class.getSimpleName();
    return "Launch " + className + " using " + fileName;
  }
}
