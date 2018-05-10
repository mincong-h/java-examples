package io.mincong.ocpjp.autoclosing;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class AutoCloseableObjectTest {

  @Test
  public void example() throws Exception {
    StringBuilder sb = new StringBuilder();
    try (AutoCloseableObject obj = new AutoCloseableObject(sb)) {
      obj.doSthWrong();
    } catch (RuntimeException e) {
      sb.append("Exception caught; ");
    }
    assertThat(sb.toString()).isEqualTo("Instantiated; Sth wrong; Closed; Exception caught; ");
  }

}
