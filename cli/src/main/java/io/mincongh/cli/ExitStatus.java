package io.mincongh.cli;

/**
 * Launcher exit status.
 *
 * @author Mincong Huang
 */
public enum ExitStatus {

  OK(0, "Program is running or server is OK"),
  ERR_UNKNOWN_ERROR(1, "Generic or unspecified error"),
  ERR_INVALID_ARGS(2, "Invalid or excess argument(s)"),
  ERR_UNKONWN_FEATURE(3, "Unimplemented feature"),
  ERR_INSUFFICIENT_PRIV(4, "User had insufficient privilege"),
  ERR_NOT_INSTALLED(5, "Program is not installed"),
  ERR_NOT_CONFIGURED(6, "Program is not configured"),
  ERR_NOT_RUNNING(7, "Program is not running");

  private int code;

  private String message;

  ExitStatus(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
