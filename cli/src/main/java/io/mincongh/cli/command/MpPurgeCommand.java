package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;
import org.apache.commons.cli.Option;

/**
 * Mp-purge: Uninstall and remove all packages from the local cache.
 *
 * @author Mincong Huang
 */
public class MpPurgeCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpPurgeCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_PURGE;
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
    return Messages.mpPurgeCommandDescription();
  }
}
