package io.mincongh.hashcode.bad;

import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Test the class {@code PhoneNumberNoHash} located in package bad, a bad class example having no
 * {@code hashCode} method.
 *
 * @author Mincong Huang
 */
public class PhoneNumberTest {

  /**
   * The {@code PhoneNumberNoHash} class's failure to override {@code hashCode} causes the two equal
   * instances to have unequal hash codes, in violation of the {@code hashCode} contract. Therefore
   * the {@code get} method is likely to look for the phone number in a different hash bucket from
   * the one in which it was stored by the {@code put} method. Even if the two instances happen to
   * hash to the same bucket, the {@code get} method will almost certainly return {@code null}, as
   * {@code HashMap} has an optimization that caches the hash code associated with each entry and
   * doesn't bother checking for object equality if the hash codes don't match.
   */
  @Test
  public void testEquality() {
    Map<PhoneNumberNoHash, String> m = new HashMap<PhoneNumberNoHash, String>();
    m.put(new PhoneNumberNoHash(707, 867, 5309), "Jenny");
    // Hash codes are not the same for these two objects.
    assertNull(m.get(new PhoneNumberNoHash(707, 867, 5309)));
  }
}
