package io.mincongh.hashcode.good;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Test the class {@code PhoneNumber} located in package good, a good class example having a
 * well-implemented {@code hashCode} method.
 *
 * @author Mincong Huang
 */
public class PhoneNumberTest {

  @Test
  public void testEquality() {
    Map<PhoneNumber, String> m = new HashMap<PhoneNumber, String>();
    m.put(new PhoneNumber(707, 867, 5309), "Jenny");
    assertEquals("Jenny", m.get(new PhoneNumber(707, 867, 5309)));
  }
}
