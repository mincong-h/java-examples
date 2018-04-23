package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;
import org.apache.commons.cli.Option;

/**
 * Mp-uninstall: Uninstall Marketplace package(s). You must provide the package name(s) or ID(s) as
 * parameter (see mp-list command). If uninstalling a package by its ID and other versions of the
 * same package are available, the most up-to-date will be installed instead.
 *
 * @author Mincong Huang
 */
public class MpUninstallCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpUninstallCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_UNINSTALL;
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
    return Messages.mpUnintallCommandDescription();
  }
}
