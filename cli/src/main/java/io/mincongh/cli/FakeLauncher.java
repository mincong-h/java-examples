package io.mincongh.cli;

import io.mincongh.cli.command.ConsoleCommand;
import io.mincongh.cli.command.StartCommand;
import io.mincongh.cli.command.StopCommand;
import io.mincongh.cli.util.Constants;

/**
 * Fake launcher is located in the "bin" folder of your server installation. It enables various
 * options and commands (explained in details below).
 *
 * @author Mincong Huang
 */
@SuppressWarnings("unused")
public final class FakeLauncher {

  private FakeLauncher() {
    // Utility class, do not instantiate.
  }

  /**
   * Runs target command with arguments.
   *
   * @param commandName the name of target command
   * @param args the additional arguments for target command, can be empty.
   * @throws IllegalStateException if failed to parse the command name
   */
  public static void run(String commandName, String... args) {
    if (Constants.COMMAND_CONSOLE.equals(commandName)) {
      new ConsoleCommand(args).call();
      return;
    }
    if (Constants.COMMAND_START.equals(commandName)) {
      new StartCommand(args).call();
      return;
    }
    if (Constants.COMMAND_STOP.equals(commandName)) {
      new StopCommand(args).call();
      return;
    }
    throw new IllegalStateException("Failed to parse command '" + commandName + "'");
  }
}
