package io.mincongh.cli.option;

import org.apache.commons.cli.Option;

/**
 * Linux-only. Activate Java debugging mode on the Launcher.
 *
 * @author Mincong Huang
 */
public interface HasDebugLauncherOption extends HasOption {

  default boolean isDebugLauncher() {
    return getCommandLine().hasOption("debug-launcher");
  }

  default Option newDebugOption() {
    return Option.builder() //
        .longOpt("debug-launcher") //
        .desc("(Since Fake 5.9.4) Linux-only. Activate Java debugging mode on the Launcher.") //
        .build();
  }
}
