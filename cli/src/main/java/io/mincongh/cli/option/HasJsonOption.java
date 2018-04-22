package io.mincongh.cli.option;

import org.apache.commons.cli.Option;

/** @author Mincong Huang */
public interface HasJsonOption extends HasOption {

  default Option newJsonOption() {
    return Option.builder() //
        .longOpt("json") //
        .desc("(Since Fake 5.6) Output JSON for marketplace commands.") //
        .build();
  }
}
