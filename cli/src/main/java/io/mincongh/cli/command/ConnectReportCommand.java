package io.mincongh.cli.command;

import org.apache.commons.cli.Option;

/**
 * Connect-report command: dumps a json report about the running server.
 *
 * @author Mincong Huang
 */
public class ConnectReportCommand extends Command<Void> {

  public ConnectReportCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.CONNECT_REPORT;
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
