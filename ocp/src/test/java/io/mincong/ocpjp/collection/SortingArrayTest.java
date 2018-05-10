package io.mincong.ocpjp.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class SortingArrayTest {

  @Test
  public void sortIntArray_full() throws Exception {
    int[] a = {5, 4, 3, 2, 1};
    Arrays.sort(a);
    assertThat(a).containsSequence(1, 2, 3, 4, 5);
  }

  @Test
  public void sortIntArray_partial() throws Exception {
    int[] a = {5, 4, 3, 2, 1};
    Arrays.sort(a, 2, 5);
    assertThat(a).containsSequence(5, 4, 1, 2, 3);
  }

  @Test
  public void sortObjectArray() throws Exception {
    String[] a = {"D", "B", "A", "C", " "};
    Arrays.sort(a);
    assertThat(a).contains(" ", "A", "B", "C", "D");
  }

}
