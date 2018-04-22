package io.mincongh.cli.command;

import org.apache.commons.cli.Option;

/**
 * Decrypt command: output decrypted value for a given parameter.
 *
 * @author Mincong Huang
 */
public class DecryptCommand extends Command<Void> {

  public DecryptCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.DECRYPT;
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
