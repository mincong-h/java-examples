package io.mincongh.cli.command;

import io.mincongh.cli.option.HasServerOptions;
import io.mincongh.cli.Messages;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.NotNull;
import org.apache.commons.cli.Option;

/**
 * Stop-command is used to stop a server.
 *
 * <pre>
 * stop [-d | --debug &lt;categories&gt;]
 *      [-q | --quiet]
 *      [--gui]
 * </pre>
 *
 * @author Mincong Huang
 */
public class StopCommand extends Command<Void> implements HasServerOptions {

  private static final Logger LOGGER = Logger.getLogger(StopCommand.class.getName());

  public StopCommand(String... args) {
    super(args);
  }

  @Override
  public @NotNull String name() {
    return Commands.STOP;
  }

  @Override
  public Iterable<Option> newOptions() {
    Set<Option> options = new HashSet<>();
    options.add(newQuietOption());
    options.add(newDebugOption());
    options.add(newGuiOption());
    return options;
  }

  @Override
  public Void call() {
    if (LOGGER.isLoggable(Level.INFO)) {
      LOGGER.info(String.format("Calling command %s (args=%s)%n", name(), Arrays.toString(args)));
    }
    validate();
    return null;
  }

  @Override
  void validate() {
    // check quiet argument
    List<String> debugCategories = getDebugCategories();
    for (String category : debugCategories) {
      if (LOGGER.isLoggable(Level.INFO)) {
        LOGGER.info("Checking debug category: " + category);
      }
    }
  }

  @Override
  public String getHelpMessage() {
    return Messages.stopCommandDescription();
  }
}
