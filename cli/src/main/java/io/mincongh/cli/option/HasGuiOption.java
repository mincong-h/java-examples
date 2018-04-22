package io.mincongh.cli.option;

import org.apache.commons.cli.Option;

/**
 * Desired behaviors when having option "gui".
 *
 * @author Mincong Huang
 */
public interface HasGuiOption extends HasOption {

  /**
   * Returns true if the graphical user interface (GUI) is enabled.
   *
   * @return true if the graphical user interface (GUI) is enabled.
   */
  default boolean hasGui() {
    return getCommandLine().hasOption("gui");
  }

  /**
   * Creates a new GUI option.
   *
   * <p>This is an internal method.
   *
   * @return a new GUI option
   */
  default Option newGuiOption() {
    return Option.builder() //
        .longOpt("gui") //
        .desc("Start the graphical user interface (GUI), aka the Fake Control Panel.") //
        .build();
  }
}
