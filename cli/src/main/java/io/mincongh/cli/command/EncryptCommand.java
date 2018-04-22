package io.mincongh.cli.command;

import org.apache.commons.cli.Option;

/**
 * Encrypt command: output encrypted value for a given parameter.
 *
 * @author Mincong Huang
 */
public class EncryptCommand extends Command<String> {

  public EncryptCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.ENCRYPT;
  }

  @Override
  void validate() {
    // TODO
  }

  @Override
  public Iterable<Option> newOptions() {
    return null;
  }

  @Override
  public String call() {
    // TODO
    return null;
  }
}
