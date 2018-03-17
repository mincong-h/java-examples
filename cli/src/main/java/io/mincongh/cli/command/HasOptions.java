package io.mincongh.cli.command;

import org.apache.commons.cli.Option;

/**
 * A collection of options.
 *
 * @author Mincong Huang
 */
public interface HasOptions {

  /**
   * Creates options.
   *
   * <p>They can be required or non-required.
   */
  Iterable<Option> newOptions();
}
