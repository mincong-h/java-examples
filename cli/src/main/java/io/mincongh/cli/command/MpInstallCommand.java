package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;

/**
 * Mp-install command: Run Marketplace package installation. It is automatically called at startup
 * if installAfterRestart.log file exists in data directory. Else you must provide the package
 * file(s), name(s) or ID(s) as parameter.
 *
 * @author Mincong Huang
 */
public class MpInstallCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpInstallCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_INSTALL;
  }

  @Override
  public Void call() {
    return null;
  }

  @Override
  void validate() {}

  @Override
  public String getHelpMessage() {
    return Messages.mpInstallCommandDescription();
  }
}
