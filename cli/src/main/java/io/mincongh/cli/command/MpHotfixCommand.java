package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;

/**
 * Mp-hotfix: Install all the available hot-fixes for the current platform, but do not upgrade
 * already installed hot-fixes versions. For the latter, please use mp-upgrade (to upgrade
 * everything) or mp-install with the desired version (to install a specific package) instead.
 * (requires a registered instance).
 *
 * @author Mincong Huang
 */
public class MpHotfixCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpHotfixCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_HOTFIX;
  }

  @Override
  public Void call() {
    return null;
  }

  @Override
  void validate() {}

  @Override
  public String getHelpMessage() {
    return Messages.mpHotfixCommandDescription();
  }
}
