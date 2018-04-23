package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;
import org.apache.commons.cli.Option;

/**
 * Mp-request: Install and uninstall Marketplace package(s) in one command. You must provide a
 * quoted list of package names or IDs prefixed with + (install) or - (uninstall).
 *
 * @author Mincong Huang
 */
public class MpRequestCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpRequestCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_REQUEST;
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
    return Messages.mpRequestCommandDescription();
  }
}
