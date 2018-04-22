package io.mincongh.cli.option;

import org.apache.commons.cli.Option;

/**
 * Desired behaviors when having option "strict".
 *
 * @author Mincong Huang
 */
public interface HasStrictOption extends HasOption {

  /** Returns true if command is in strict mode. */
  default boolean isStrict() {
    return getCommandLine().hasOption("strict");
  }

  /**
   * Creates a new start option.
   *
   * <p>This is an internal method.
   *
   * @return a new start option
   */
  default Option newStrictOption() {
    return Option.builder() //
        .longOpt("strict") //
        .desc(
            "Abort in error the start command when a component cannot be activated or if a server is already running.") //
        .build();
  }
}
