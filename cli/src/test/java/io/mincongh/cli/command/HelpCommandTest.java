package io.mincongh.cli.command;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Tests help command.
 *
 * @author Mincong Huang
 */
public class HelpCommandTest {

  @Test
  public void call() {
    assertThat(new HelpCommand().call()).isNotEmpty();
  }
}
