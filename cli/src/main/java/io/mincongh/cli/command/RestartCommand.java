package io.mincongh.cli.command;

import io.mincongh.cli.option.HasServerOptions;
import io.mincongh.cli.Messages;

/**
 * Restart-command.
 *
 * @author Mincong Huang
 */
public class RestartCommand extends Command<Void> implements HasServerOptions {

  public RestartCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.RESTART;
  }

  @Override
  void validate() {
    // TODO
  }

  @Override
  public String getHelpMessage() {
    return Messages.restartCommandDescription();
  }

  @Override
  public Void call() {
    // TODO
    return null;
  }
}
