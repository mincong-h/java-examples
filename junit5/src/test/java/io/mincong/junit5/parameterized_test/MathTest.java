package io.mincong.junit5.parameterized_test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MathTest {
  @ParameterizedTest
  @CsvSource({
    "1,  2, 2",
    "1, -1, 1",
    "1,  1, 1",
  })
  void testMax(int a, int b, int max) {
    assertThat(Math.max(a, b)).isEqualTo(max);
  }
}
