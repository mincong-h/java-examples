package io.mincong.ocpjp.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.junit.Test;

/** @author Mincong Huang */
public class TreeSetTest {

  // TreeSetTest$Person cannot be cast to class java.lang.Comparable
  @Test(expected = ClassCastException.class)
  public void addElementWithoutSpecifyingComparator() {
    TreeSet<Person> treeSet = new TreeSet<>();
    treeSet.add(new Person("A"));
  }

  @Test
  public void addElementWithSpecifyingComparator() {
    TreeSet<Person> treeSet = new TreeSet<>(Comparator.comparing(Person::getName));
    treeSet.add(new Person("A"));
    treeSet.add(new Person("B"));

    String result = treeSet.stream().map(Person::getName).collect(Collectors.joining(", "));
    assertThat(result).isEqualTo("A, B");
  }

  private static class Person {

    private String name;

    Person(String name) {
      this.name = name;
    }

    String getName() {
      return name;
    }
  }
}
