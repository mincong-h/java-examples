package io.mincongh.cli.command;

import org.apache.commons.cli.Option;

/**
 * Print this message.
 *
 * @author Mincong Huang
 */
public class HelpCommand extends Command<Void> {

  public HelpCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.HELP;
  }

  @Override
  void validate() {
    // TODO
  }

  @Override
  public Iterable<Option> newOptions() {
    // TODO
    return null;
  }

  @Override
  public Void call() {
    // TODO
    return null;
  }
}
