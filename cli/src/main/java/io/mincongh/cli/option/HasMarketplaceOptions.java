package io.mincongh.cli.option;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.cli.Option;

/**
 * A collection of options used for marketplace commands.
 *
 * @author Mincong Huang
 */
public interface HasMarketplaceOptions
    extends HasOptions,
        HasClidOption,
        HasDebugOption,
        HasHelpOption,
        HasJsonOption,
        HasQuietOption,
        HasXmlOption {

  default Iterable<Option> newOptions() {
    Set<Option> options = new HashSet<>();
    options.add(newClidOption());
    options.add(newDebugOption());
    options.add(newHelpOption());
    options.add(newJsonOption());
    options.add(newQuietOption());
    options.add(newXmlOption());
    return options;
  }
}
