package io.mincong.ocpjp.design_principles.factory.simple;

/** @author Mincong Huang */
public class TextEditor implements App {

  @Override
  public String getResult(String fileName) {
    String className = this.getClass().getSimpleName();
    return "Launch " + className + " using " + fileName;
  }
}
