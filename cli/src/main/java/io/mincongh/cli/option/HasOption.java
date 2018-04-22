package io.mincongh.cli.option;

import org.apache.commons.cli.CommandLine;

/**
 * Base interface for all the has-option interfaces.
 *
 * @author Mincong Huang
 */
public interface HasOption {

  CommandLine getCommandLine();
}
