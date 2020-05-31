package io.mincongh.testing.junit;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import org.junit.Test;

/**
 * Java unit test parameterization array
 *
 * @author Mincong Huang
 */
public class So49333280Test {

  @Test
  public void quickSort() {
    int[] in = {1, 4, 6, 3, 5, 4};
    int[] expected = {1, 3, 4, 4, 5, 6};
    Arrays.sort(in);
    assertArrayEquals(expected, in);
  }
}
