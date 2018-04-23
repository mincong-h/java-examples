package io.mincongh.cli.command;

import io.mincongh.cli.option.HasHelpOption;
import io.mincongh.cli.Messages;
import java.util.Collections;
import org.apache.commons.cli.Option;

/**
 * Config command: get and set template or global parameters.
 *
 * @author Mincong Huang
 */
public class ConfigCommand extends Command<Void> implements HasHelpOption {

  public ConfigCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.CONFIG;
  }

  @Override
  void validate() {
    // TODO
  }

  @Override
  public Iterable<Option> newOptions() {
    // TODO
    return Collections.emptyList();
  }

  @Override
  public Void call() {
    // TODO
    return null;
  }

  @Override
  public String getHelpMessage() {
    return Messages.configCommandDescription();
  }
}
