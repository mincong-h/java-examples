package io.mincongh.inheritance2;

import io.mincongh.inheritance.Parent;

public class OuterChild extends Parent {

  public String publicAttr = "outer-child public";
  protected String protectedAttr = "outer-child protected";
  String noModifierAttr = "outer-child no modifier";
  private String privateAttr = "outer-child private";

  public OuterChild() {
    System.out.println("OuterChild");
  }

  public String getPrivateAttr() {
    return privateAttr;
  }

  public void setPrivateAttr(String privateAttr) {
    this.privateAttr = privateAttr;
  }
}
