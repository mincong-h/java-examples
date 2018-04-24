package io.mincongh.cli.command;

import io.mincongh.cli.Messages;
import io.mincongh.cli.option.HasServerOptions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.NotNull;
import org.apache.commons.cli.Option;

/**
 * Start-command is used to start a server.
 *
 * @author Mincong Huang
 */
public class StartCommand extends Command<Void> implements HasServerOptions {

  private static final Logger LOGGER = Logger.getLogger(StartCommand.class.getName());

  public StartCommand(String... args) {
    super(args);
  }

  @Override
  public Iterable<Option> newOptions() {
    Set<Option> options = new HashSet<>();
    options.add(newDebugOption());
    options.add(newQuietOption());
    options.add(newClidOption());
    options.add(newGuiOption());
    options.add(newStrictOption());
    return options;
  }

  @Override
  void validate() {
    if (hasClid()) {
      validateClid(getClid());
    }
  }

  @Override
  public Void call() {
    boolean started = doStart();
    if (started && !isQuiet()) {
      LOGGER.info("Go to " + getUrl());
    }
    return null;
  }

  // TODO Implement configuration generator
  private String getUrl() {
    return "http://localhost:8080";
  }

  private boolean doStart() {
    String javaCommand = "java";

    // Java command
    if (hasGui()) {
      javaCommand += " MyGuiApp";
    } else {
      javaCommand += " MyCliApp";
    }

    // Java options
    List<String> javaOpts = new ArrayList<>();
    if (isDebug()) {
      javaOpts.add("-Xdebug");
    }
    if (isStrict()) {
      javaOpts.add("-Dstrict");
    }
    if (hasClid()) {
      javaOpts.add("-Dclid=" + getClid());
    }
    javaCommand += ' ' + String.join(" ", javaOpts);

    if (!isQuiet() && LOGGER.isLoggable(Level.INFO)) {
      LOGGER.info("Starting server...");
      LOGGER.info("command=\"" + javaCommand + "\"");
    }
    return true;
  }

  @Override
  public @NotNull String name() {
    return Commands.START;
  }

  @Override
  public String getHelpMessage() {
    return Messages.startCommandDescription();
  }
}
