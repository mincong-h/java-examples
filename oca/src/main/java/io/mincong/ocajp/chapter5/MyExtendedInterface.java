package io.mincong.ocajp.chapter5;

/**
 * @author Mincong Huang
 */
public interface MyExtendedInterface extends MyInterface {

  @Override
  void myMethod();

  // abstract method is safely ignored:
  // void canBeIgnored();

}
