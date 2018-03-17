package io.mincongh.cli.command;

import org.apache.commons.cli.CommandLine;

/**
 * Base interface for all the has-option interfaces.
 *
 * @author Mincong Huang
 */
public interface HasOption {

  CommandLine getCommandLine();
}
