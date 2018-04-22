package io.mincongh.cli.option;

import org.apache.commons.cli.Option;

/**
 * @author Mincong Huang
 */
public interface HasXmlOption extends HasOption {

  default Option newXmlOption() {
    return Option.builder() //
        .longOpt("xml") //
        .desc("(Since Fake 5.6) Output XML for marketplace commands.") //
        .build();
  }
}
