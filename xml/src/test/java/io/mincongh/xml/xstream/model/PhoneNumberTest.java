package io.mincongh.xml.xstream.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PhoneNumberTest {

  @Test
  void toStr() {
    var phoneNumber = new PhoneNumber(1, "2345");
    assertThat(String.valueOf(phoneNumber)).isEqualTo("+01 2345");
  }
}
