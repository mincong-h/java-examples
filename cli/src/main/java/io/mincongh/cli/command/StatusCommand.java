package io.mincongh.cli.command;

import org.apache.commons.cli.Option;

/**
 * Status command: print server running status.
 *
 * @author Mincong Huang
 */
public class StatusCommand extends Command<Integer> {

  public StatusCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.STATUS;
  }

  @Override
  public Integer call() {
    return null;
  }

  @Override
  void validate() {}

  @Override
  public Iterable<Option> newOptions() {
    return null;
  }
}
