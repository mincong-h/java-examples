package io.mincongh.cli;

/**
 * Launcher exit codes. These values follow the Linux Standard Base Core Specification 4.1
 *
 * <p>If the status command is requested, launcher will turn the following exit status codes:
 *
 * <table>
 *   <tr>
 *     <td>0</td><td>Program is running or service is OK.</td>
 *   </tr>
 *   <tr>
 *     <td>3</td><td>Program is not running.</td>
 *   </tr>
 *   <tr>
 *     <td>4</td><td>Program or service status is unknown.</td>
 *   </tr>
 * </table>
 *
 * <p>In case of an error while processing any action except for status, launcher will print an
 * error message and exit with a non-zero status code:
 *
 * <table>
 *   <tr>
 *     <td>1</td><td>Generic or unspecified error</td>
 *   </tr>
 *   <tr>
 *     <td>2</td><td>Invalid or excess argument(s)</td>
 *   </tr>
 *   <tr>
 *     <td>3</td><td>Unimplemented feature</td>
 *   </tr>
 *   <tr>
 *     <td>4</td><td>User had insufficient privilege</td>
 *   </tr>
 *   <tr>
 *     <td>5</td><td>Program is not installed</td>
 *   </tr>
 *   <tr>
 *     <td>6</td><td>Program is not configured</td>
 *   </tr>
 *   <tr>
 *     <td>7</td><td>Program is not running</td>
 *   </tr>
 * </table>
 *
 * @author Mincong Huang
 */
public class ExitCode {

  /** Program is running or server is OK. */
  public static final int OK = 0;

  /** Generic or unspecified error. * */
  public static final int ERR_UNKNOWN_ERROR = 1;

  /** Invalid or excess argument(s). */
  public static final int ERR_INVALID_ARGS = 2;

  /** Unimplemented feature. */
  public static final int ERR_UNKNOWN_FEATURE = 3;

  /** User had insufficient privilege. */
  public static final int ERR_INSUFFICIENT_PRIV = 4;

  /** Program is not installed. */
  public static final int ERR_NOT_INSTALLED = 5;

  /** Program is not configured. */
  public static final int ERR_NOT_CONFIGURED = 6;

  /** Program is not running. */
  public static final int ERR_NOT_RUNNING = 7;

  private ExitCode() {
    // Utility class, do not instantiate.
  }
}
