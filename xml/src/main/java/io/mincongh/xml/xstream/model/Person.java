package io.mincongh.xml.xstream.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Mincong Huang
 */
@XStreamAlias("person")
public class Person {

  private String firstName;

  private String lastName;

  private int age;

  private PhoneNumber phoneNumber;

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Person)) {
      return false;
    }

    Person person = (Person) o;

    if (age != person.age) {
      return false;
    }
    if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) {
      return false;
    }
    if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) {
      return false;
    }
    return phoneNumber != null ? phoneNumber.equals(person.phoneNumber)
        : person.phoneNumber == null;
  }

  @Override
  public int hashCode() {
    int result = firstName != null ? firstName.hashCode() : 0;
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + age;
    result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Person{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", age=" + age +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }

}
