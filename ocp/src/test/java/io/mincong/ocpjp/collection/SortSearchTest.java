package io.mincong.ocpjp.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class SortSearchTest {

  @Test
  public void binarySearch() throws Exception {
    int[] a = {1, 2, 4, 5};
    int idx2 = Arrays.binarySearch(a, 2);
    int idx3 = Arrays.binarySearch(a, 3);

    int insertIdx = 2;
    assertThat(idx2).isEqualTo(1);
    assertThat(idx3).isEqualTo(- 1 - insertIdx);
  }

}
