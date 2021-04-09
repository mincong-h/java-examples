package io.mincong.junit5.parameterized_test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FilePermissionTest {
  @ParameterizedTest
  @CsvSource({
    "R, false",
    "W, false",
    "RW, false",
    "X, true",
    "RX, true",
    "WX, true",
    "RWX, true",
  })
  void isExecutable(FilePermission perm, boolean isExecutable) {
    assertThat(perm.isExecutable()).isEqualTo(isExecutable);
  }

  @ParameterizedTest
  @CsvSource({
    "W, false",
    "X, false",
    "WX, false",
    "R, true",
    "RW, true",
    "RX, true",
    "RWX, true",
  })
  void isReadable(FilePermission perm, boolean isReadable) {
    assertThat(perm.isReadable()).isEqualTo(isReadable);
  }

  @ParameterizedTest
  @CsvSource({
    "X, false",
    "R, false",
    "RX, false",
    "W, true",
    "WX, true",
    "RW, true",
    "RWX, true",
  })
  void isWritable(FilePermission perm, boolean isWritable) {
    assertThat(perm.isWritable()).isEqualTo(isWritable);
  }
}
