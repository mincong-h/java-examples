package io.mincongh.hashcode.googleinterview;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Test the class {@code PhoneNumber} located in package google interview, a bad example having a
 * worst possible legal {@code hashCode} method (every object has the same hash code). Here's an
 * explanation of the complexity from
 * {@link http://stackoverflow.com/questions/4553624/hashmap-get-put-complexity} .
 * <p>
 * The HashMap get / put complexity depends on many things. It's usually O(1), with a decent hash
 * which itself is constant time... but you could have a hash which takes a long time to compute,
 * and if there are multiple items in the hash map which return the same hash code, get will have to
 * iterate over them calling equals on each of them to find a match.
 * <p>
 * In the worst case, a HashMap has an O(n) lookup due to walking through all entries in the same
 * hash bucket (e.g. if they all have the same hash code). Fortunately, that worst case scenario
 * doesn't come up very often in real life, in my experience. So no, O(1) certainly isn't guaranteed
 * - but it's usually what you should assume when considering which algorithms and data structures
 * to use.
 * <p>
 * In JDK 8, HashMap has been tweaked so that if keys can be compared for ordering, then any
 * densely-populated bucket is implemented as a tree, so that even if there are lots of entries with
 * the same hash code, the complexity is O(log n). That can cause issues if you have a key type
 * where equality and ordering are different, of course.
 * <p>
 * And yes, if you don't have enough memory for the hash map, you'll be in trouble... but that's
 * going to be true whatever data structure you use.
 *
 * @author Jon Skeet
 * @author Mincong Huang
 */
public class PhoneNumberTest {

  /**
   * Each object of {@code PhoneNumber} has the same hash code. Therefore, all the entries are
   * stored in the same hash bucket. In this case, the {@code get} operation is
   *
   * <ul>
   * <li>{@literal O(n)} in Java 7
   * <li>{@literal O(log n)} in Java 8.
   * </ul>
   *
   * Results in Java 8:
   *
   * <pre>
   * -------------------------------
   * Same hash code for each object.
   * t=3605ns
   * t=110851ns (x30.75 times slower)
   * -------------------------------
   * </pre>
   */
  @Test
  public void testHashMap_sameHashCode() {
    Map<io.mincongh.hashcode.bad.PhoneNumber, String> m =
        new HashMap<io.mincongh.hashcode.bad.PhoneNumber, String>();
    m.put(new io.mincongh.hashcode.bad.PhoneNumber(707, 867, 5309), "Jenny");

    long start1 = System.nanoTime();
    String actualValue1 = m.get(new io.mincongh.hashcode.bad.PhoneNumber(707, 867, 5309));
    long end1 = System.nanoTime();
    System.out.println("-------------------------------");
    System.out.println("Same hash code for each object.");
    System.out.printf("t=%dns%n", end1 - start1);
    assertEquals("Jenny", actualValue1);

    // Insert 10,000 entries
    for (int a = 0; a < 100; a++) {
      for (int b = 0; b < 100; b++) {
        String val = String.format("%d,%d,%d", a, b, 0);
        m.put(new io.mincongh.hashcode.bad.PhoneNumber(a, b, 0), val);
      }
    }

    long start2 = System.nanoTime();
    String actualValue2 = m.get(new io.mincongh.hashcode.bad.PhoneNumber(707, 867, 5309));
    long end2 = System.nanoTime();
    double times = (end2 - start2) * 1.0 / (end1 - start1);
    System.out.printf("t=%dns (x%.2f times slower)%n", end2 - start2, times);
    System.out.println("-------------------------------");
    assertEquals("Jenny", actualValue2);
  }

  /**
   * Each object of {@code PhoneNumber} has the different hash code. Therefore, all the entries are
   * stored in the different hash bucket. In this case, the {@code get} operation is
   *
   * <ul>
   * <li>{@literal O(1)} in Java 7
   * <li>{@literal O(1)} in Java 8.
   * </ul>
   *
   * Results in Java 8:
   * 
   * <pre>
   * -------------------------------
   * Different hash code for each object.
   * t=11108ns
   * t=2175ns (x0.20 times slower)
   * -------------------------------
   * </pre>
   */
  @Test
  public void testHashMap_diffHashCode() {
    Map<io.mincongh.hashcode.good.PhoneNumber, String> m =
        new HashMap<io.mincongh.hashcode.good.PhoneNumber, String>();
    m.put(new io.mincongh.hashcode.good.PhoneNumber(707, 867, 5309), "Jenny");

    long start1 = System.nanoTime();
    String actualValue1 = m.get(new io.mincongh.hashcode.good.PhoneNumber(707, 867, 5309));
    long end1 = System.nanoTime();
    System.out.println("-------------------------------");
    System.out.println("Different hash code for each object.");
    System.out.printf("t=%dns%n", end1 - start1);
    assertEquals("Jenny", actualValue1);

    // Insert 10,000 entries
    for (int a = 0; a < 100; a++) {
      for (int b = 0; b < 100; b++) {
        String val = String.format("%d,%d,%d", a, b, 0);
        m.put(new io.mincongh.hashcode.good.PhoneNumber(a, b, 0), val);
      }
    }

    long start2 = System.nanoTime();
    String actualValue2 = m.get(new io.mincongh.hashcode.good.PhoneNumber(707, 867, 5309));
    long end2 = System.nanoTime();
    double times = (end2 - start2) * 1.0 / (end1 - start1);
    System.out.printf("t=%dns (x%.2f times slower)%n", end2 - start2, times);
    System.out.println("-------------------------------");
    assertEquals("Jenny", actualValue2);
  }
}
