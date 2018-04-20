package io.mincongh.cli;

import io.mincongh.cli.command.ConsoleCommand;
import io.mincongh.cli.command.StartCommand;
import io.mincongh.cli.command.StopCommand;
import io.mincongh.cli.util.Constants;
import java.util.Arrays;

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
   * @return exit status
   * @throws IllegalStateException if failed to parse the command name
   */
  private static ExitStatus run(String commandName, String... args) {
    if (Constants.COMMAND_CONSOLE.equals(commandName)) {
      new ConsoleCommand(args).call();
      return ExitStatus.ERR_NOT_INSTALLED;
    }
    if (Constants.COMMAND_START.equals(commandName)) {
      new StartCommand(args).call();
      return ExitStatus.ERR_NOT_INSTALLED;
    }
    if (Constants.COMMAND_STOP.equals(commandName)) {
      new StopCommand(args).call();
      return ExitStatus.ERR_NOT_INSTALLED;
    }
    throw new IllegalStateException("Failed to parse command '" + commandName + "'");
  }

  /**
   * Entry point of the application.
   */
  public static void main(String[] args) {
    String commandName = args[0];
    String[] arguments = Arrays.copyOfRange(args, 1, args.length);
    ExitStatus status;
    try {
      status = run(commandName, arguments);
    } catch (IllegalStateException e) {
      status = ExitStatus.ERR_INVALID_ARGS;
    }
    if (status != ExitStatus.OK) {
      System.exit(status.getCode());
    }
  }
}
