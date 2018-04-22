package io.mincongh.cli.option;

import org.apache.commons.cli.Option;

/**
 * Show detailed help message.
 *
 * @author Mincong Huang
 */
public interface HasHelpOption extends HasOption {

  default boolean hasHelp() {
    return getCommandLine().hasOption("h");
  }

  /**
   * Gets the help message for command.
   */
  String getHelpMessage();

  default Option newHelpOption() {
    return Option.builder("h") //
        .longOpt("help")
        .desc("Show detailed help message.")
        .build();
  }
}
