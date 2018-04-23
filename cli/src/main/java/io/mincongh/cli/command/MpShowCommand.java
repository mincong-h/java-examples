package io.mincongh.cli.command;

import io.mincongh.cli.option.HasMarketplaceOptions;
import io.mincongh.cli.Messages;
import org.apache.commons.cli.Option;

/**
 * Mp-show: (Since Fake 5.7.1) Show Marketplace package(s) information. You must provide the
 * package file(s), name(s) or ID(s) as parameter.
 *
 * @author Mincong Huang
 */
public class MpShowCommand extends Command<Void> implements HasMarketplaceOptions {

  public MpShowCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.MP_SHOW;
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
    return Messages.mpShowCommandDescription();
  }
}
