package io.mincongh.cli.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for commands.
 *
 * @author Mincong Huang
 */
public final class Commands {

  /* ---------- Command names ---------- */

  public static final String CONFIG = "config";
  public static final String CONFIGURE = "configure";
  public static final String CONNECT_REPORT = "connect-report";
  public static final String CONSOLE = "console";
  public static final String DECRYPT = "decrypt";
  public static final String ENCRYPT = "encrypt";
  public static final String GUI = "gui"; // deprecated
  public static final String HELP = "help";
  public static final String MP_ADD = "mp-add";
  public static final String MP_INIT = "mp-init";
  public static final String MP_INSTALL = "mp-install";
  public static final String MP_HOTFIX = "mp-hotfix";
  public static final String MP_LIST = "mp-list";
  public static final String MP_PURGE = "mp-purge";
  public static final String MP_REMOVE = "mp-remove";
  public static final String MP_REQUEST = "mp-request";
  public static final String MP_RESET = "mp-reset";
  public static final String MP_SET = "mp-set";
  public static final String MP_SHOW = "mp-show";
  public static final String MP_UNINSTALL = "mp-uninstall";
  public static final String MP_UPDATE = "mp-update";
  public static final String MP_UPGRADE = "mp-upgrade";
  public static final String PACK = "pack";
  public static final String REGISTER = "register";
  public static final String RESTART = "restart";
  public static final String RESTARTBG = "restartbg";
  public static final String SHOW_CONF = "showconf";
  public static final String START = "start";
  public static final String STARTBG = "startbg";
  public static final String STATUS = "status";
  public static final String STOP = "stop";
  public static final String WIZARD = "wizard";

  private Commands() {
    // Utility class, do not instantiate.
  }

  public static Set<String> supportedCommands() {
    Set<String> commands = allCommands();
    commands.removeAll(deprecatedCommands());
    return commands;
  }

  private static Set<String> allCommands() {
    return new HashSet<>(
        Arrays.asList(
            CONFIG,
            CONFIGURE,
            CONNECT_REPORT,
            CONSOLE,
            DECRYPT,
            ENCRYPT,
            GUI,
            HELP,
            MP_ADD,
            MP_INIT,
            MP_INSTALL,
            MP_HOTFIX,
            MP_LIST,
            MP_PURGE,
            MP_REMOVE,
            MP_REQUEST,
            MP_RESET,
            MP_SET,
            MP_SHOW,
            MP_UNINSTALL,
            MP_UPDATE,
            MP_UPGRADE,
            PACK,
            REGISTER,
            RESTART,
            RESTARTBG,
            SHOW_CONF,
            START,
            STARTBG,
            STATUS,
            STOP,
            WIZARD));
  }

  private static Set<String> deprecatedCommands() {
    return new HashSet<>(Collections.singletonList(GUI));
  }
}
