package io.mincongh.cli.command;

import io.mincongh.cli.Messages;
import java.util.Collections;
import org.apache.commons.cli.Option;

/**
 * Print help message.
 *
 * @author Mincong Huang
 */
public class HelpCommand extends Command<String> {

  public HelpCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.HELP;
  }

  @Override
  void validate() {
    // Do nothing
  }

  @Override
  public Iterable<Option> newOptions() {
    return Collections.emptyList();
  }

  @Override
  public String call() {
    StringBuilder sb = new StringBuilder();
    Commands.supportedCommands()
        .stream()
        .sorted()
        .forEach(
            cmd -> sb.append(String.format("%-17s  %s%n", cmd, Messages.getCommandDesc(cmd))));
    return sb.toString();
  }
}
