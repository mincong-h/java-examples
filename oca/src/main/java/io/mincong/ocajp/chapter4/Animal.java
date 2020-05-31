package io.mincong.ocajp.chapter4;

/** @author Mincong Huang */
public class Animal {

  private double weight;

  private String name;

  public Animal(String name, double wight) {
    this.name = name;
    this.weight = wight;
  }

  public double getWeight() {
    return weight;
  }

  public String getName() {
    return name;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public void setName(String name) {
    this.name = name;
  }
}
