package io.mincong.ocajp.introduction.cake;

import static io.mincong.ocajp.introduction.cake.CakeFactory.Chocolate;
import static io.mincong.ocajp.introduction.cake.CakeFactory.Strawberry;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Change object field value.
 *
 * @author Mincong HUANG
 */
public class FieldValueTest {

  @Test
  public void testFieldValueModification() {
    StringBuilder builder = new StringBuilder();
    Cake c = new Cake();
    Strawberry(c);
    builder.append(c.model).append("-").append(c.flavor).append(",");
    Chocolate(c);
    builder.append(c.model).append("-").append(c.flavor);
    assertEquals("1200-Strawberry,1230-Chocolate", builder.toString());
  }
}
