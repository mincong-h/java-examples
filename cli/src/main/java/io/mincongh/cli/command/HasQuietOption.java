package io.mincongh.cli.command;

import org.apache.commons.cli.Option;

/**
 * Desired behaviors when having option "quiet".
 *
 * @author Mincong Huang
 */
public interface HasQuietOption extends HasOption {

  /**
   * Returns true if the quiet flag is set.
   *
   * @return true if the quiet flag is set.
   */
  default boolean isQuiet() {
    return getCommandLine().hasOption("q");
  }

  /**
   * Creates a new quiet option.
   *
   * <p>This is an internal method.
   *
   * @return a new quiet option
   */
  default Option newQuietOption() {
    return Option.builder("q") //
        .longOpt("quiet") //
        .desc("Suppress information messages.") //
        .build();
  }
}
