package io.mincongh.cli.command;

import org.apache.commons.cli.Option;

/**
 * Wizard command: starts the wizard.
 *
 * @author Mincong Huang
 */
public class WizardCommand extends Command<Void> {

  public WizardCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.WIZARD;
  }

  @Override
  void validate() {}

  @Override
  public Iterable<Option> newOptions() {
    return null;
  }

  @Override
  public Void call() {
    return null;
  }
}
