package io.mincongh.zoo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Mincong Huang
 */
@Entity
public class Animal implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private int id;
  private String name;

  public Animal() {

  }

  public Animal(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }
}
