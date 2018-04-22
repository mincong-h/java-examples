package io.mincongh.cli.command;

import io.mincongh.cli.option.HasServerOptions;
import io.mincongh.cli.util.Messages;

/**
 * Starts fake server in a console mode.
 *
 * <p>Press "CTRL" + "C" will stop it.
 *
 * @author Mincong Huang
 */
public class ConsoleCommand extends Command<Void> implements HasServerOptions {

  public ConsoleCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.CONSOLE;
  }

  @Override
  void validate() {
    // Do nothing.
  }

  @Override
  public Void call() {
    return null;
  }

  @Override
  public String getHelpMessage() {
    return Messages.consoleCommandDescription();
  }
}
