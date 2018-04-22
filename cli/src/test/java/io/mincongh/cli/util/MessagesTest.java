package io.mincongh.cli.util;

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
  public void description() {
    for (String cmd : Commands.supportedCommands()) {
      String key = "launcher.command." + cmd + ".description";
      String msg = "Command '" + cmd + "' is supported, but its key is missing in properties file.";
      assertThat(Messages.getMessage(key)).as(msg).isNotEmpty();
    }
  }
}
