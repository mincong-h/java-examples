package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;
import org.apache.commons.cli.Option;

/**
 * Mp-add command: add Marketplace package(s) to local cache. You must provide the package file(s),
 * name(s) or ID(s) as parameter.
 *
 * @author Mincong Huang
 */
public class MpAddCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpAddCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_ADD;
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

  @Override
  public String getHelpMessage() {
    return Messages.mpAddCommandDescription();
  }
}
