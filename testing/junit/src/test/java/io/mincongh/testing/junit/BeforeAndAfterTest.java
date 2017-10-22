package io.mincongh.testing.junit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class BeforeAndAfterTest {

  private List<String> users;

  @Before
  public void setUp() {
    users = new ArrayList<>();
    users.add("A");
    users.add("B");
    users.add("C");
  }

  @After
  public void tearDown() {
    users = null;
  }

  /**
   * Before the test, the list {@code users} has been set / reset to {@literal ["A", "B", "C"]}.
   */
  @Test
  public void testAdd() {
    users.add("D");
    assertEquals(4, users.size());
    assertEquals("A", users.get(0));
    assertEquals("B", users.get(1));
    assertEquals("C", users.get(2));
    assertEquals("D", users.get(3));
  }

  /**
   * Before the test, the list {@code users} has been set / reset to {@literal ["A", "B", "C"]}.
   */
  @Test
  public void testRemove() {
    users.remove(0);
    assertEquals(2, users.size());
    assertEquals("B", users.get(0));
    assertEquals("C", users.get(1));
  }

}
