package io.mincongh.rest.dto;

/**
 * User DTO.
 *
 * @author Mincong Huang
 */
public class User {

  private int id;

  private String name;

  private int age;

  @SuppressWarnings("unused")
  private User() {
    // Used for serialization
  }

  public User(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public User(int id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return this.id == user.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
