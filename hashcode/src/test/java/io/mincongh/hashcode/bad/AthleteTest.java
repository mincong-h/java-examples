package io.mincongh.hashcode.bad;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.mincongh.hashcode.good.Athlete;

/**
 * This is an interview question from <i>Coursera - Introduction to Algorithms, part I</i>, week 6
 * Hash Tables: Hashing with wrong {@code hashCode()} or {@code equals()}. Suppose that you
 * implement a data type {@code Athlete} for use in a {@code java.util.HashMap}.
 *
 * <ul>
 * <li>Describe what happens if you override {@code hashCode()} but not {@code equals()}.
 * <li>Describe what happens if you override {@code equals()} but not {@code hashCode()}.
 * <li>Describe what happens if you override {@code hashCode()} but implement
 * {@code public boolean equals(OlympicAthlete that)} instead of public boolean equals(Object that).
 * </ul>
 *
 * @author Mincong Huang
 */
public class AthleteTest {

  /**
   * Test the phenomenon where model only overrides the {@code hashCode()} but not the
   * {@code equals()}. This is called <b>"collisions"</b>, when two non-equal objects have the same
   * hash code.
   */
  @Test
  public void testCollisions() {

    AthleteOnlyOverrideHashcode a1 = new AthleteOnlyOverrideHashcode("hashcode@gmail.com", "Hash");
    AthleteOnlyOverrideHashcode a2 = new AthleteOnlyOverrideHashcode("hashcode@gmail.com", "Code");
    Map<AthleteOnlyOverrideHashcode, String> map = new HashMap<>();
    map.put(a1, a1.toString());
    map.put(a2, a2.toString());

    // Collisions!
    // a1 and a2 are different but still have the same hash-code.
    // This will show down the performance of the hash table, because multiple objects have the same
    // hash code, java need to iterate them the find the equation.
    assertEquals("Athlete [email=hashcode@gmail.com, name=Hash]", map.get(a1).toString());
    assertEquals("Athlete [email=hashcode@gmail.com, name=Code]", map.get(a2).toString());
    assertTrue(a1.hashCode() == a2.hashCode());
    assertTrue(!a1.equals(a2));
  }

  @Test
  public void testWrongHashcode() {

    AthleteOnlyOverrideEquals b1 = new AthleteOnlyOverrideEquals("hashcode@gmail.com", "Hash Code");
    AthleteOnlyOverrideEquals b2 = new AthleteOnlyOverrideEquals("hashcode@gmail.com", "Hash Code");
    Map<AthleteOnlyOverrideEquals, String> map = new HashMap<>();
    map.put(b1, b1.toString());
    map.put(b2, b2.toString());

    assertEquals("Athlete [email=hashcode@gmail.com, name=Hash Code]", map.get(b1).toString());
    assertEquals("Athlete [email=hashcode@gmail.com, name=Hash Code]", map.get(b2).toString());

    // Horrible!
    // b1 and b2 have the same attributes, but they've difference hashCode()
    // In real application, key b1 should be overwritten by b2
    assertTrue(b1.hashCode() != b2.hashCode());
    assertTrue(b1.equals(b2));
  }

  /**
   * In the following test, we can see that c1 is overwritten by c2 in the map, because we consider
   * that c1 and c2 are equal. Therefore, there're only one entry matched to these 2 objects, the
   * later one, c2.
   */
  @Test
  public void testCorrectImpl() {
    Athlete c1 = new Athlete("hashcode@gmail.com", "Hash Code");
    Athlete c2 = new Athlete("hashcode@gmail.com", "Hash");
    Map<Athlete, String> map = new HashMap<>();
    map.put(c1, c1.toString());
    map.put(c2, c2.toString()); // overwrite

    assertEquals("Athlete [email=hashcode@gmail.com, name=Hash Code]", c1.toString());
    assertEquals("Athlete [email=hashcode@gmail.com, name=Hash]", map.get(c1).toString());
    assertEquals("Athlete [email=hashcode@gmail.com, name=Hash]", map.get(c2).toString());
    assertTrue(c1.hashCode() == c2.hashCode());
    assertTrue(c1.equals(c2));
  }
}
