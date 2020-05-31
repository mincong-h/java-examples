package io.mincong.ocpjp.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** @author Mincong Huang */
public class ArrayListTest {

  private static final ArrayList<Integer> arrayList = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    Integer a = 20;
    Integer b = 20;
    arrayList.add(a);
    arrayList.add(b);
  }

  @After
  public void tearDown() throws Exception {
    arrayList.clear();
  }

  @Test
  public void add() throws Exception {
    assertThat(arrayList).hasSize(2);
  }

  @Test
  public void removeByReference() throws Exception {
    // When removing an nonexistent object from the array list
    Integer nonexistent = 30;
    arrayList.remove(nonexistent);

    // Then the array list still has the same size (no exception thrown)
    assertThat(arrayList).hasSize(2);

    // When removing an exiting object
    Integer existing = 20;
    arrayList.remove(existing);

    // Then the size of array list decremented
    assertThat(arrayList).hasSize(1);
  }

  @Test
  public void removeByIndex() throws Exception {
    try {
      arrayList.remove(30);
      fail("Remove an element by an nonexistent index should raise an exception.");
    } catch (IndexOutOfBoundsException e) {
      // Ok
    }
    assertThat(arrayList).hasSize(2);
  }

  @Test
  public void removeAll() throws Exception {
    Integer a = 20;
    arrayList.removeAll(Collections.singletonList(a));
    assertThat(arrayList).isEmpty();
  }
}
