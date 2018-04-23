package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;
import org.apache.commons.cli.Option;

/**
 * Mp-remove: Remove Marketplace package(s). You must provide the package name(s) or ID(s) as
 * parameter (see mp-list command).
 *
 * @author Mincong Huang
 */
public class MpRemoveCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpRemoveCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_REMOVE;
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
    return Messages.mpRemoveCommandDescription();
  }
}
