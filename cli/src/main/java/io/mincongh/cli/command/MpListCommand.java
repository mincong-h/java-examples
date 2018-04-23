package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;
import org.apache.commons.cli.Option;

/**
 * Mp-list command: List local Marketplace packages.
 *
 * @author Mincong Huang
 */
public class MpListCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpListCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_LIST;
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
    return Messages.mpListCommandDescription();
  }
}
