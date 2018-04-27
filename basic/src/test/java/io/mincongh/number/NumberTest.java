package io.mincongh.number;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/** @author Mincong Huang */
public class NumberTest {

  @Test
  @SuppressWarnings("squid:S3415")
  public void testUnderscore() {
    assertEquals(10, 1_0);
    assertEquals(1000, 1_000);
    assertEquals(1000000, 1_000_000);
    assertEquals(0.000001, 0.000_001, 0.0000000000001);
  }

  /* --- IEEE Standard for Floating-Point Arithmetic (IEEE 754) --- */

  @Test
  public void float_IEEE_754() {
    // Long#toBinaryString(float)
    assertThat(Integer.toBinaryString(Float.floatToIntBits(0.1f))) //
        .isEqualTo("111101110011001100110011001101");
    assertThat(Integer.toBinaryString(Float.floatToIntBits(0.2f))) //
        .isEqualTo("111110010011001100110011001101");
    assertThat(Integer.toBinaryString(Float.floatToIntBits(0.3f))) //
        .isEqualTo("111110100110011001100110011010");
    assertThat(Integer.toBinaryString(Float.floatToIntBits(0.1f + 0.2f))) //
        .isEqualTo("111110100110011001100110011010");

    // This#toBinaryString32(float)
    assertThat(toBinaryString32(0.1f)) //
        .isEqualTo("00111101110011001100110011001101");
    assertThat(toBinaryString32(0.2f)) //
        .isEqualTo("00111110010011001100110011001101");
    assertThat(toBinaryString32(0.3f)) //
        .isEqualTo("00111110100110011001100110011010");
    assertThat(toBinaryString32(0.1f + 0.2f)) //
        .isEqualTo("00111110100110011001100110011010");
  }

  @Test
  public void double_IEEE_754() {
    // Long#toBinaryString(double)
    assertThat(Long.toBinaryString(Double.doubleToLongBits(0.1d))) //
        .isEqualTo("11111110111001100110011001100110011001100110011001100110011010");
    assertThat(Long.toBinaryString(Double.doubleToLongBits(0.2d))) //
        .isEqualTo("11111111001001100110011001100110011001100110011001100110011010");
    assertThat(Long.toBinaryString(Double.doubleToLongBits(0.3d))) //
        .isEqualTo("11111111010011001100110011001100110011001100110011001100110011");
    assertThat(Long.toBinaryString(Double.doubleToLongBits(0.1d + 0.2d))) //
        .isEqualTo("11111111010011001100110011001100110011001100110011001100110100");

    // This#toBinaryString64(double)
    assertThat(toBinaryString64(0.1d)) //
        .isEqualTo("0011111110111001100110011001100110011001100110011001100110011010");
    assertThat(toBinaryString64(0.2d)) //
        .isEqualTo("0011111111001001100110011001100110011001100110011001100110011010");
    assertThat(toBinaryString64(0.3d)) //
        .isEqualTo("0011111111010011001100110011001100110011001100110011001100110011");
    assertThat(toBinaryString64(0.1d + 0.2d)) //
        .isEqualTo("0011111111010011001100110011001100110011001100110011001100110100");
  }

  private static String toBinaryString32(float number) {
    String binaryStr = Integer.toBinaryString(Float.floatToIntBits(number));
    return String.format("%32s", binaryStr).replace(' ', '0');
  }

  private static String toBinaryString64(double number) {
    String binaryStr = Long.toBinaryString(Double.doubleToLongBits(number));
    return String.format("%64s", binaryStr).replace(' ', '0');
  }
}
