package io.mincongh.cli.command;

import org.apache.commons.cli.Option;

/** @author Mincong Huang */
public class RegisterCommand extends Command<Void> {

  public RegisterCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return null;
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
