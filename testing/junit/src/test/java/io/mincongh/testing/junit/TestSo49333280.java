package io.mincongh.testing.junit;

import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Java unit test parameterization array
 *
 * @author Mincong Huang
 */
public class TestSo49333280 {

  @Test
  public void quickSort() {
    int[] in = {1, 4, 6, 3, 5, 4};
    int[] expected = {1, 3, 4, 4, 5, 6};
    Arrays.sort(in);
    assertArrayEquals(expected, in);
  }
}
