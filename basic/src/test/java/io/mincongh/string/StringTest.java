package io.mincongh.string;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {

  @Test
  public void lastIndexOf() {
    assertThat("hello".lastIndexOf('.')).isEqualTo(-1);
  }

  @Test(expected = StringIndexOutOfBoundsException.class)
  public void substring() {
    "hello".substring(0, -1);
  }

}
