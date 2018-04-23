package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;
import org.apache.commons.cli.Option;

/**
 * Mp-reset command: Reset all packages to DOWNLOADED state. May be useful after a manual server
 * upgrade.
 *
 * @author Mincong Huang
 */
public class MpResetCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpResetCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_RESET;
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
    return Messages.mpResetCommandDescription();
  }
}
