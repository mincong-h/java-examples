package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;
import org.apache.commons.cli.Option;

/**
 * Mp-update command: Update cache of Marketplace packages list.
 *
 * @author Mincong Huang
 */
public class MpUpdateCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpUpdateCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_UPDATE;
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
    return Messages.mpUpdateCommandDescription();
  }
}
