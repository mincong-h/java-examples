package io.mincongh.cli;

import io.mincongh.cli.command.Commands;
import io.mincongh.cli.command.ConfigCommand;
import io.mincongh.cli.command.ConfigureCommand;
import io.mincongh.cli.command.ConnectReportCommand;
import io.mincongh.cli.command.ConsoleCommand;
import io.mincongh.cli.command.DecryptCommand;
import io.mincongh.cli.command.EncryptCommand;
import io.mincongh.cli.command.HelpCommand;
import io.mincongh.cli.command.MpAddCommand;
import io.mincongh.cli.command.MpHotfixCommand;
import io.mincongh.cli.command.MpInitCommand;
import io.mincongh.cli.command.MpInstallCommand;
import io.mincongh.cli.command.MpListCommand;
import io.mincongh.cli.command.MpPurgeCommand;
import io.mincongh.cli.command.MpRemoveCommand;
import io.mincongh.cli.command.MpRequestCommand;
import io.mincongh.cli.command.MpResetCommand;
import io.mincongh.cli.command.MpSetCommand;
import io.mincongh.cli.command.MpShowCommand;
import io.mincongh.cli.command.MpUninstallCommand;
import io.mincongh.cli.command.MpUpdateCommand;
import io.mincongh.cli.command.MpUpgradeCommand;
import io.mincongh.cli.command.PackCommand;
import io.mincongh.cli.command.RegisterCommand;
import io.mincongh.cli.command.RestartCommand;
import io.mincongh.cli.command.ShowConfCommand;
import io.mincongh.cli.command.StartCommand;
import io.mincongh.cli.command.StatusCommand;
import io.mincongh.cli.command.StopCommand;
import io.mincongh.cli.command.WizardCommand;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Fake launcher is located in the "bin" folder of your server installation. It enables various
 * options and commands (explained in details below).
 *
 * @author Mincong Huang
 */
public final class FakeLauncher {

  private static final Logger LOGGER = Logger.getLogger(FakeLauncher.class.getName());

  private FakeLauncher() {}

  public void start() {
    // TODO Implement this method
  }

  public void stop() {
    // TODO Implement this method
  }
  /**
   * Runs target command with arguments.
   *
   * @param cmd the name of target command
   * @param args the additional arguments for target command, can be empty.
   * @return exit code
   * @throws IllegalStateException if failed to parse the command name
   */
  @SuppressWarnings("squid:S106")
  private static int run(String cmd, String... args) {
    FakeLauncher launcher = new FakeLauncher();

    // Server commands
    if (Commands.CONSOLE.equals(cmd)) {
      try (ConsoleCommand c = new ConsoleCommand(args).withLauncher(launcher)) {
        c.call().get();
        return ExitCode.OK;
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      } catch (ExecutionException e) {
        LOGGER.severe(e.getMessage());
        return ExitCode.ERR_UNKNOWN_ERROR;
      }
    }
    if (Commands.RESTART.equals(cmd)) {
      new RestartCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.START.equals(cmd)) {
      new StartCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.STOP.equals(cmd)) {
      new StopCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.STATUS.equals(cmd)) {
      new StatusCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.STARTBG.equals(cmd)) {
      // TODO Convert to start command?
      new StartCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.RESTARTBG.equals(cmd)) {
      // TODO Convert to restart command?
      new RestartCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }

    // Environment commands
    if (Commands.SHOW_CONF.equals(cmd)) {
      new ShowConfCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }

    // Marketplace commands
    if (Commands.MP_ADD.equals(cmd)) {
      new MpAddCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_INIT.equals(cmd)) {
      new MpInitCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_INSTALL.equals(cmd)) {
      new MpInstallCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_HOTFIX.equals(cmd)) {
      new MpHotfixCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_LIST.equals(cmd)) {
      new MpListCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_PURGE.equals(cmd)) {
      new MpPurgeCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_REMOVE.equals(cmd)) {
      new MpRemoveCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_REQUEST.equals(cmd)) {
      new MpRequestCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_RESET.equals(cmd)) {
      new MpResetCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_SET.equals(cmd)) {
      new MpSetCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_SHOW.equals(cmd)) {
      new MpShowCommand(args);
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_UNINSTALL.equals(cmd)) {
      new MpUninstallCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_UPDATE.equals(cmd)) {
      new MpUpdateCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.MP_UPGRADE.equals(cmd)) {
      new MpUpgradeCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }

    // Other commands
    if (Commands.HELP.equals(cmd)) {
      String s = new HelpCommand(args).call();
      System.out.println(s);
      return ExitCode.OK;
    }
    if (Commands.GUI.equals(cmd)) {
      // TODO Deprecated command
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.CONFIG.equals(cmd)) {
      new ConfigCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.ENCRYPT.equals(cmd)) {
      List<String> values = new EncryptCommand(args).call();
      values.forEach(System.out::println);
      return ExitCode.OK;
    }
    if (Commands.DECRYPT.equals(cmd)) {
      new DecryptCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.CONFIGURE.equals(cmd)) {
      new ConfigureCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.WIZARD.equals(cmd)) {
      new WizardCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.PACK.equals(cmd)) {
      new PackCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.CONNECT_REPORT.equals(cmd)) {
      new ConnectReportCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    if (Commands.REGISTER.equals(cmd)) {
      new RegisterCommand(args).call();
      return ExitCode.ERR_UNKNOWN_FEATURE;
    }
    throw new IllegalStateException("Failed to parse command '" + cmd + "'");
  }

  /** Entry point of the application. */
  public static void main(String[] args) {
    String commandName = args[0];
    String[] arguments = Arrays.copyOfRange(args, 1, args.length);
    int status;
    try {
      status = run(commandName, arguments);
    } catch (IllegalStateException e) {
      LOGGER.severe(e.getMessage());
      status = ExitCode.ERR_INVALID_ARGS;
    }
    if (status != ExitCode.OK) {
      System.exit(status);
    }
  }
}
