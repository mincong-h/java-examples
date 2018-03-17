package io.mincongh.cli.command;

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
        HasQuietOption,
        HasDebugOption,
        HasGuiOption,
        HasStrictOption,
        HasClidOption {

  default Iterable<Option> newOptions() {
    Set<Option> options = new HashSet<>();
    options.add(newQuietOption());
    options.add(newDebugOption());
    options.add(newGuiOption());
    options.add(newStrictOption());
    options.add(newClidOption());
    return options;
  }
}
