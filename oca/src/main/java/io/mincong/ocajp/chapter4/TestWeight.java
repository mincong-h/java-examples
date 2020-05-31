package io.mincong.ocajp.chapter4;

/** @author Mincong Huang */
public class TestWeight implements TestMe {

  @Override
  public boolean test(Animal animal) {
    return animal.getWeight() > 50;
  }
}
