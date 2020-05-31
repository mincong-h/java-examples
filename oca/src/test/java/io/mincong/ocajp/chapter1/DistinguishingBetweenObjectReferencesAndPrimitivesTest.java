package io.mincong.ocajp.chapter1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** @author Mincong Huang */
public class DistinguishingBetweenObjectReferencesAndPrimitivesTest {

  @Test
  public void testNumberBase() {
    assertEquals(15, 017); // octal
    assertEquals(2, 0b10);
    assertEquals(15, 0xF);
  }

  /** Review Questions > Question 16. */
  @Test
  public void testNumberBaseConversion() {
    int amount1 = 0b101;
    int amount2 = 0xE;
    double amount3 = 0xE;
    assertEquals(amount1, 5);
    assertEquals(amount2, 14);
    assertEquals(amount3, 14, 0.000_000_1);
  }

  /**
   * The following expressions do not compile (except the last one):
   *
   * <pre>
   * double notAtStart = _1000.00;          // DOES NOT COMPILE
   * double notAtEnd = 1000.00_;            // DOES NOT COMPILE
   * double notByDecimal = 1000_.00;        // DOES NOT COMPILE
   * double annoyingButLegal = 1_00_0.0_0;  // OK
   * </pre>
   */
  @Test
  public void testNumericLiteralsInJava7() {
    assertEquals(1_000_000, 1000 * 1000);
    assertEquals(0.000_001, 0.001 * 0.001, 0.000_000_1);
  }
}
