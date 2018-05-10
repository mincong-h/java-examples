package io.mincong.ocpjp.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class TreeSetTest {

  @Test
  public void addElementWithoutSpecifyingComparator() throws Exception {
    TreeSet<Person> treeSet = new TreeSet<>();
    try {
      treeSet.add(new Person("A"));
      fail();
    } catch (ClassCastException e) {
      assertThat(e)
          .hasMessageMatching(".*?Person cannot be cast to .*?java\\.lang\\.Comparable");
    }
  }

  @Test
  public void addElementWithSpecifyingComparator() throws Exception {
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
