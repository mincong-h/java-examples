package io.mincong.ocpjp.design_principles.factory.method;

/**
 * @author Mincong Huang
 */
public class TextEditor implements App {

  @Override
  public String getResult(String fileName) {
    String className = getClass().getSimpleName();
    return "Launch " + className + " using " + fileName;
  }

}
