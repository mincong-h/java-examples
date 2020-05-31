package io.mincong.ocpjp.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.stream.Collectors;
import org.junit.Test;

/** @author Mincong Huang */
public class HashSetTest {

  private static final String[] names = {"A", "B", "C", "B"};

  /*
   * Start: Tests for `HashSet#add(Object)`
   *
   * When elements are added to class `HashSet`, it queries method
   * `hashCode()` of the element to get the bucket in which the
   * element would be stored. If the bucket doesn't contain any
   * elements, it stores the new element in the bucket. If the
   * bucket already contains elements, `HashSet` checks for matching
   * `hashCode` values, compares object references, or queries method
   * `equals()` to ensure that it stores unique values.
   */

  /**
   * In absence of the overridden methods {@link Person#equals(Object)} and {@link
   * Person#hashCode()}, two reference variables are equals if and only if their reference are the
   * same: <code>return (this == obj);</code>. Since each person instance is instantiated, and none
   * of them is referenced from an existing object, they're considered as different, so all of them
   * are added into the hash set.
   */
  @Test
  public void missingHashCodeAndEquals() throws Exception {
    HashSet<Person> people = new HashSet<>();
    for (String name : names) {
      people.add(new Person__(name));
    }
    String result = people.stream().map(Person::toString).collect(Collectors.joining(","));
    assertThat(result).containsOnlyOnce("A");
    assertThat(result).containsOnlyOnce("C");
    assertThat(result).contains("B");
  }

  /**
   * In absence of the overridden method {@link Person#equals(Object)}, even though all the
   * reference variables have the same {@link PersonH_#hashCode()} values, they aren't considered
   * equal. So all objects referred by these variables are added to {@link HashSet}.
   */
  @Test
  public void sameHashCodeDoesNotMeanEquals() throws Exception {
    HashSet<Person> people = new HashSet<>();
    for (String name : names) {
      people.add(new PersonH_(name));
    }
    String result = people.stream().map(Person::toString).collect(Collectors.joining(","));
    assertThat(result).containsOnlyOnce("A");
    assertThat(result).containsOnlyOnce("C");
    assertThat(result).contains("B"); // B has been added twice.
  }

  /**
   * Class {@link PersonHE} does not define an appropriately overridden method {@link
   * PersonHE#equals(Object)}, it returns true for any object compared with a {@link PersonHE}
   * instance.
   *
   * <p>So when comparing to each other, reference variables always return true. The elements
   * attempting to be added after the first element are all considered as duplicate. Therefore,
   * there're only one element inside the hash set.
   */
  @Test
  public void sameHashCodeAndEqualsAlwaysTrue() throws Exception {
    HashSet<Person> people = new HashSet<>();
    for (String name : names) {
      people.add(new PersonHE(name));
    }
    String result = people.stream().map(Person::toString).collect(Collectors.joining(","));
    assertThat(result).containsOnlyOnce("A");
    assertThat(result).doesNotContain("B");
    assertThat(result).doesNotContain("C");
  }

  /**
   * Even though class {@link Person_E} overrides the method {@link Person#equals(Object)}
   * correctly, it does not override the {@link Person#hashCode()}}. Notice that hash code is the
   * key point for getting the correct bucket in which the element should be stored. Now, each
   * person object has its own hash code: thus all the reference variables are added into the hash
   * set.
   */
  @Test
  public void onlyOverrideEquals() throws Exception {
    HashSet<Person> people = new HashSet<>();
    for (String name : names) {
      people.add(new Person_E(name));
    }
    String result = people.stream().map(Person::toString).collect(Collectors.joining(","));
    assertThat(result).containsOnlyOnce("A");
    assertThat(result).containsOnlyOnce("C");
    assertThat(result).contains("B"); // B has been added twice.
  }

  /* End: Tests for `HashSet#add(Object)` */

  private abstract static class Person {

    private String name;

    Person(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }

    String getName() {
      return name;
    }
  }

  /** Neither {@link #equals(Object)} nor {@link #hashCode()} is overridden. */
  private static class Person__ extends Person {

    Person__(String name) {
      super(name);
    }
  }

  /**
   * Only {@link #hashCode()} is overridden, but the hash code implementation is very inefficient.
   * It always return the same value, so when adding an instance of this class into a hash set, it
   * will always fall into the same bucket. Then hash set will need to determine if the instance is
   * equals to or different from the existing instances.
   */
  private static class PersonH_ extends Person {

    PersonH_(String name) {
      super(name);
    }

    @Override
    public int hashCode() {
      return 10;
    }
  }

  /** Only {@link #equals(Object)} is overridden. */
  private static class Person_E extends Person {

    Person_E(String name) {
      super(name);
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Person_E) {
        Person_E that = (Person_E) o;
        return this.getName().equals(that.getName());
      } else {
        return false;
      }
    }
  }

  /**
   * Both {@link #hashCode()} and {@link #equals(Object)} are overridden, but their implementations
   * are incorrect. All the instances comparison result to true... Yes, always result to true
   * because {@link #equals(Object)} return true.
   */
  private static class PersonHE extends Person {

    PersonHE(String name) {
      super(name);
    }

    @Override
    public int hashCode() {
      return 10;
    }

    @Override
    public boolean equals(Object o) {
      return true;
    }
  }
}
