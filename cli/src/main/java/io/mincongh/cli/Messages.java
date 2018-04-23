package io.mincongh.cli;

import java.util.ResourceBundle;

/**
 * Internationalized messages.
 *
 * @author Mincong Huang
 */
public final class Messages {

  /** Currently, only default messages are supported. */
  private static ResourceBundle r = ResourceBundle.getBundle("i18n.launcher");

  private Messages() {
    // Utility class, do not instantiate.
  }

  /* ---------- Commands ---------- */

  public static String configCommandDescription() {
    return r.getString("launcher.command.config.description");
  }

  public static String configureCommandDescription() {
    return r.getString("launcher.command.configure.description");
  }

  public static String consoleCommandDescription() {
    return r.getString("launcher.command.console.description");
  }

  public static String connectReportCommandDescription() {
    return r.getString("launcher.command.connect-report.description");
  }

  public static String decryptCommandDescription() {
    return r.getString("launcher.command.decrypt.description");
  }

  public static String encryptCommandDescription() {
    return r.getString("launcher.command.encrypt.description");
  }

  public static String helpCommandDescription() {
    return r.getString("launcher.command.help.description");
  }

  public static String mpAddCommandDescription() {
    return r.getString("launcher.command.mp-add.description");
  }

  public static String mpHotfixCommandDescription() {
    return r.getString("launcher.command.mp-hotfix.description");
  }

  public static String mpInitCommandDescription() {
    return r.getString("launcher.command.mp-init.description");
  }

  public static String mpInstallCommandDescription() {
    return r.getString("launcher.command.mp-install.description");
  }

  public static String mpListCommandDescription() {
    return r.getString("launcher.command.mp-list.description");
  }

  public static String mpListAllCommandDescription() {
    return r.getString("launcher.command.mp-listall.description");
  }

  public static String mpPurgeCommandDescription() {
    return r.getString("launcher.command.mp-purge.description");
  }

  public static String mpRemoveCommandDescription() {
    return r.getString("launcher.command.mp-remove.description");
  }

  public static String mpRequestCommandDescription() {
    return r.getString("launcher.command.mp-request.description");
  }

  public static String mpResetCommandDescription() {
    return r.getString("launcher.command.mp-reset.description");
  }

  public static String mpSetCommandDescription() {
    return r.getString("launcher.command.mp-set.description");
  }

  public static String mpShowCommandDescription() {
    return r.getString("launcher.command.mp-show.description");
  }

  public static String mpUnintallCommandDescription() {
    return r.getString("launcher.command.mp-uninstall.description");
  }

  public static String mpUpdateCommandDescription() {
    return r.getString("launcher.command.mp-update.description");
  }

  public static String mpUpgradeCommandDescription() {
    return r.getString("launcher.command.mp-upgrade.description");
  }

  public static String packCommandDescription() {
    return r.getString("launcher.command.pack.description");
  }

  public static String restartCommandDescription() {
    return r.getString("launcher.command.restart.description");
  }

  public static String restartBgCommandDescription() {
    return r.getString("launcher.command.restartbg.description");
  }

  public static String showConfCommandDescription() {
    return r.getString("launcher.command.showconf.description");
  }

  public static String registerCommandDescription() {
    return r.getString("launcher.command.register.description");
  }

  public static String startCommandDescription() {
    return r.getString("launcher.command.start.description");
  }

  public static String startBgCommandDescription() {
    return r.getString("launcher.command.startbg.description");
  }

  public static String statusCommandDescription() {
    return r.getString("launcher.command.status.description");
  }

  public static String stopCommandDescription() {
    return r.getString("launcher.command.stop.description");
  }

  public static String wizardCommandDescription() {
    return r.getString("launcher.command.wizard.description");
  }

  public static String getCommandDesc(String cmd) {
    return r.getString("launcher.command." + cmd + ".description");
  }

  public static String getOptionDesc(String opt) {
    return r.getString("launcher.option." + opt + ".description");
  }
}
