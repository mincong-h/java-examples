package io.mincongh.cli.command;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests encrypt command.
 *
 * @author Mincong Huang
 */
public class EncryptCommandTest {

  @Test
  public void call() {
    EncryptCommand cmd = new EncryptCommand("A");
    assertThat(cmd.call()).containsExactly("A");
  }

  @Test
  public void encryptMultipleValues() {
    EncryptCommand cmd = new EncryptCommand("--encrypt", "foo", "A", "B");
    assertThat(cmd.call()).containsExactly("fooA", "fooB");
  }
}
