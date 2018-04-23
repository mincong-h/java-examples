package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;

/**
 * Mp-set: (Since Fake 5.9.2) Install a list of Marketplace packages and remove those not in the list.
 *
 * @author Mincong Huang
 */
public class MpSetCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpSetCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_SET;
  }

  @Override
  public Void call() {
    return null;
  }

  @Override
  void validate() {

  }

  @Override
  public String getHelpMessage() {
    return Messages.mpSetCommandDescription();
  }
}
