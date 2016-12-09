package io.mincongh.clone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * This test aims to assert the behavior of {@code clone()} in different situations.
 *
 * @author Mincong Huang
 */
public class CloneTest {

  /**
   * Test the difference-by-reference between {@code array} & {@code clone}. Test the
   * equality-by-value of each element.
   */
  @Test
  public void testArray1D() {

    int[] array = new int[] {1, 2, 3};
    int[] clone = array.clone();
    int[] byref = array;

    assertTrue(array == byref);
    assertTrue(array != clone);
    for (int i = 0; i < 3; i++) {
      assertTrue(array[i] == clone[i]);
      assertTrue(array[i] == byref[i]);
    }
  }

  /**
   * In a 2-dimensions array, the situation is different, because each element of the array
   * {@code array[i]} is not a primitive type anymore.
   * <p>
   * For simple clone, the new array object is cloned using method {@code clone()}, so {@code array}
   * and {@code simpleClone} are different by reference. <b>However, they still point to the same
   * rows.</b> This is because: calling the method {@code clone()} only takes affect to the target
   * object of the target class type, but it doesn't take affect to every encapsulated objects.
   * <p>
   * For deep clone, the new array object is created by us, so {@code array} and {@code deepClone}
   * are different by reference. <b>Additionally, their encapsulated objects are different too!</b>
   * Each row had been cloned by calling the method {@code clone()} repeatedly. So even if we modify
   * a value of the original array, the deep-cloned's value won't be changed. They're stored in
   * different places in the memory.
   */
  @Test
  public void testArray2D() {

    int[][] array = new int[][] {{1, 2, 3}, {4, 5, 6}};
    int[][] simpleClone = array.clone();
    int[][] deepClone = new int[2][];

    for (int row = 0; row < 2; row++) {
      deepClone[row] = array[row].clone();
    }

    assertTrue(array != simpleClone);
    assertTrue(array != deepClone);

    for (int row = 0; row < 2; row++) {
      assertTrue(array[row] == simpleClone[row]);
      assertTrue(array[row] != deepClone[row]);

      for (int col = 0; col < 3; col++) {
        assertTrue(array[row][col] == simpleClone[row][col]);
        assertTrue(array[row][col] == deepClone[row][col]);
      }
    }

    array[1][2] = 7;
    assertEquals(7, simpleClone[1][2]);
    assertEquals(6, deepClone[1][2]);
  }

  @Test
  public void testAnimal() {

    Animal[] animals = new Animal[] {new Animal("cat"), new Animal("dog")};
    Animal[] clone = animals.clone();

    assertTrue(animals != clone);
    for (int i = 0; i < 2; i++) {
      assertTrue(animals[i] == clone[i]);
    }

    for (int i = 0; i < 2; i++) {
      clone[i] = animals[i].clone();
    }

    for (int i = 0; i < 2; i++) {
      assertTrue(animals[i] != clone[i]); // different by reference
      assertTrue(animals[i].equals(clone[i])); // equal by value
    }
  }

  private class Animal implements Cloneable {

    private String name;

    Animal(String name) {
      this.name = name;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Animal other = (Animal) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (name == null) {
        if (other.name != null)
          return false;
      } else if (!name.equals(other.name))
        return false;
      return true;
    }

    private CloneTest getOuterType() {
      return CloneTest.this;
    }

    @Override
    public Animal clone() {
      try {
        return (Animal) super.clone();
      } catch (CloneNotSupportedException e) {
        throw new AssertionError(); // Can't happen
      }
    }
  }
}
