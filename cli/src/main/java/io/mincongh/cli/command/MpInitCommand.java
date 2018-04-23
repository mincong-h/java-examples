package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;
import org.apache.commons.cli.Option;

/**
 * Mp-init command: Pre-cache Marketplace packages locally available in the distribution.
 *
 * @author Mincong Huang
 */
public class MpInitCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpInitCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_INIT;
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
    return Messages.mpInitCommandDescription();
  }
}
