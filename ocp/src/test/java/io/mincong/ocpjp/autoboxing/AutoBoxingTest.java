package io.mincong.ocpjp.autoboxing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class AutoBoxingTest {

  @Test
  public void autoBoxing() throws Exception {
    Double d = new Double(1.0D);
    /*
     * Comparison using the method below against the one in comment
     * area (defined by class `Double`):
     *
     *     public int compareTo(Double another)
     *
     * They're the same. The Java compiler uses auto-boxing to
     * convert the primitive `double` to an object of class `Double`
     * by using method `valueOf()`. So it works correctly. This is
     * done at runtime:
     *
     *     Double d = new Double(1.0D);
     *     int result = d.compareTo(Double.valueOf(2.0D));
     *
     */
    int result = d.compareTo(2.0);
    assertThat(result).isNegative();
  }

}
