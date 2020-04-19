package io.mincongh.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringTest {

  @Test
  void lastIndexOf() {
    assertThat("hello".lastIndexOf('.')).isEqualTo(-1);
  }

  @Test
  void substring() {
    assertThatThrownBy(() -> "hello".substring(0, -1))
        .isInstanceOf(StringIndexOutOfBoundsException.class);
  }
}
