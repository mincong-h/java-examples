package io.mincongh.cli.command;

import org.apache.commons.cli.Option;

/**
 * Show-conf command: display the server configuration.
 *
 * @author Mincong Huang
 */
public class ShowConfCommand extends Command<Void> {

  public ShowConfCommand(String... args) {
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
