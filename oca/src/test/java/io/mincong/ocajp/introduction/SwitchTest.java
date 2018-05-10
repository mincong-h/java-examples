package io.mincong.ocajp.introduction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Mincong HUANG
 */
public class SwitchTest {

  @Test
  public void testSwitch() {
    StringBuilder builder = new StringBuilder();
    String choice = "A";
    switch (choice) {
      case "a":
        builder.append("lowercase ");
      default:
        builder.append("default ");
      case "A":
        builder.append("uppercase ");
    }
    assertEquals("uppercase ", builder.toString());
  }
}
