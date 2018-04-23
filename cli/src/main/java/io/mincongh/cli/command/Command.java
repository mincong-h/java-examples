package io.mincongh.cli.command;

import io.mincongh.cli.option.HasOption;
import io.mincongh.cli.option.HasOptions;
import java.util.concurrent.Callable;
import javax.validation.constraints.NotNull;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Command interface describes the default behavior of an fake command.
 *
 * @param <T> the result type of method {@code call}
 * @author Mincong Huang
 */
public abstract class Command<T> implements Callable<T>, HasOption, HasOptions {

  protected final CommandLine cmd;

  private final Options options = new Options();

  final String[] args;

  public Command(String... args) {
    this.args = args;

    // Options
    newOptions().forEach(options::addOption);

    // Parse arguments
    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      formatter.printHelp("fake-ctl " + name(), options);
      throw new IllegalStateException(e);
    }

    validate();
  }

  /**
   * Gets the command name.
   *
   * <p>A valid command name is composed by lowercase characters [a-z]+, other characters are not
   * allowed.
   */
  @NotNull
  abstract String name();

  @Override
  public CommandLine getCommandLine() {
    return cmd;
  }

  @Override
  public abstract T call();

  /** Validates the command line arguments. */
  abstract void validate();
}
