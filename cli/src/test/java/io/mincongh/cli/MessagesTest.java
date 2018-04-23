package io.mincongh.cli;

import io.mincongh.cli.command.Commands;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test i18n message loading.
 *
 * @author Mincong Huang
 */
public class MessagesTest {

  @Test
  public void commandDescription() {
    for (String cmd : Commands.supportedCommands()) {
      assertThat(Messages.getCommandDesc(cmd)).isNotEmpty();
    }
  }

  @Test
  public void optionDescription() {
    assertThat(Messages.getOptionDesc("encrypt")).isNotEmpty();
  }
}
