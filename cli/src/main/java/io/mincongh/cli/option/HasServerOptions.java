package io.mincongh.cli.option;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.cli.Option;

/**
 * A collection of options used for server control.
 *
 * <p>For example, options for start, stop, or restart a server.
 *
 * @author Mincong Huang
 */
public interface HasServerOptions
    extends HasOptions,
        HasClidOption,
        HasDebugOption,
        HasGuiOption,
        HasHelpOption,
        HasQuietOption,
        HasStrictOption {

  default Iterable<Option> newOptions() {
    Set<Option> options = new HashSet<>();
    options.add(newClidOption());
    options.add(newDebugOption());
    options.add(newGuiOption());
    options.add(newHelpOption());
    options.add(newQuietOption());
    options.add(newStrictOption());
    return options;
  }
}
