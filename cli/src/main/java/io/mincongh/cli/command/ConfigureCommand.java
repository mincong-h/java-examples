package io.mincongh.cli.command;

import org.apache.commons.cli.Option;

/**
 * Configure command: configure Fake Server with parameters from fake.conf.
 *
 * @author Mincong Huang
 */
public class ConfigureCommand extends Command<Void> {

  public ConfigureCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.CONFIGURE;
  }

  @Override
  void validate() {}

  @Override
  public Iterable<Option> newOptions() {
    return null;
  }

  @Override
  public Void call() {
    return null;
  }
}
