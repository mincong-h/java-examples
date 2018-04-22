package io.mincongh.cli.command;

import org.apache.commons.cli.Option;

/** @author Mincong Huang */
public class PackCommand extends Command<Void> {

  public PackCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.PACK;
  }

  @Override
  public Void call() {
    return null;
  }

  @Override
  void validate() {}

  @Override
  public Iterable<Option> newOptions() {
    return null;
  }
}
