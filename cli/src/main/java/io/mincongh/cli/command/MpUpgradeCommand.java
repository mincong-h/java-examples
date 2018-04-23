package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;
import org.apache.commons.cli.Option;

/**
 * Mp-upgrade command: Get all the available upgrades for the Fake Packages currently installed
 * (including installed hotfixes and Studio package(s)). If a snapshot package is installed, it will
 * not be reinstalled even if it has been updated. In this case, you should use mp-remove then
 * mp-install instead.
 *
 * @author Mincong Huang
 */
public class MpUpgradeCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpUpgradeCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_UPGRADE;
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
    return Messages.mpUpgradeCommandDescription();
  }
}
