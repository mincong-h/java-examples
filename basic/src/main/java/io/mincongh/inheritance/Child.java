package io.mincongh.inheritance;

/**
 * @author Mincong Huang
 */
public class Child extends Parent {

  public String publicAttr = "child public";
  protected String protectedAttr = "child protected";
  String noModifierAttr = "child no modifier";
  private String privateAttr = "child private";

  Child() {
    System.out.println("Child");
  }

  // Child () {
  // System.out.println("Child");
  // super(); // Error! Constructor call must be the first statement in a constructor
  // }

  public String getPrivateAttr() {
    return privateAttr;
  }

  public void setPrivateAttr(String privateAttr) {
    this.privateAttr = privateAttr;
  }
}
