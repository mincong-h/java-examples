package io.mincong.ocpjp.design_principles.factory.abstract_;

/** @author Mincong Huang */
public class TextEditor implements App {

  @Override
  public String getResult(String fileName) {
    String className = getClass().getSimpleName();
    return "Launch " + className + " using " + fileName;
  }
}
