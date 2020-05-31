package io.mincong.ocajp.chapter1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** @author Mincong Huang */
public class DeclaringMultipleVariablesTest {

  @Test
  public void testMultipleDeclarations() {
    int i1, i2, i3 = 0;
    // Variable 'i1' was not initialized.
    // Variable 'i2' was not initialized.
    assertEquals(i3, 0);
  }

  @Test
  public void testMultipleInitializations() throws Exception {
    int i1;
    int i2;
    int i3;
    i1 = i2 = i3 = 5;
    assertEquals(i1, 5);
    assertEquals(i2, 5);
    assertEquals(i3, 5);
  }
}
