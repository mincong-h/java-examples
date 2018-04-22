package io.mincongh.cli.option;

import org.apache.commons.cli.Option;

/** @author Mincong Huang */
public interface HasClidOption extends HasOption {

  default boolean hasClid() {
    return getCommandLine().hasOption("clid");
  }

  default String getClid() {
    return getCommandLine().getOptionValue("clid");
  }

  default Option newClidOption() {
    return Option.builder() //
        .longOpt("clid")
        .hasArg() //
        .argName("clid")
        .desc("Use the provided instance CLID file.")
        .build();
  }

  default void validateClid(String clid) {
    String msg = "CLID is not valid, it must be a SHA-1 value (clid=" + clid + ").";
    if (clid.length() != 40) {
      throw new IllegalStateException(msg);
    }
    for (char c : clid.toCharArray()) {
      if ("0123456789abcdef".indexOf(c) < 0) {
        throw new IllegalStateException(msg);
      }
    }
  }
}
