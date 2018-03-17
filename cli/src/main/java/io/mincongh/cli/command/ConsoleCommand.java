package io.mincongh.cli.command;

import io.mincongh.cli.util.Constants;

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
    return Constants.COMMAND_CONSOLE;
  }

  @Override
  void validate() {
    // Do nothing.
  }

  @Override
  public Void call() {
    return null;
  }
}
