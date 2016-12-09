package io.mincongh.inheritance;

/**
 * @author Mincong Huang
 */
public class Parent {

  public String publicAttr = "parent public";
  protected String protectedAttr = "parent protected";
  String noModifierAttr = "parent no modifier";
  private String privateAttr = "parent private";

  protected Parent() {
    System.out.println("Parent");
  }

  public String getPrivateAttr() {
    return privateAttr;
  }

  public void setPrivateAttr(String privateAttr) {
    this.privateAttr = privateAttr;
  }
}
