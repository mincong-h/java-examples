package io.mincong.ocajp.introduction.cake;

/**
 * Change object field value.
 *
 * @see https://magiclen.org/ocajp-change-object-field-value/
 */
public class CakeFactory {

  public static Cake Strawberry(Cake c) {
    c.flavor = "Strawberry";
    c.model = 1200;
    return c;
  }

  public static void Chocolate(Cake c) {
    c.flavor = "Chocolate";
    c.model = 1230;
    return;
  }
}
