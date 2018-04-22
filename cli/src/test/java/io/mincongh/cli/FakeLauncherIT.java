package io.mincongh.cli;

import java.io.File;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for Launcher.
 *
 * @author Mincong Huang
 */
public class FakeLauncherIT {

  private final String launcher = FakeLauncher.class.getCanonicalName();

  private String javaBin;

  private String classPath;

  private ProcessBuilder processBuilder;

  @Before
  public void setUp() throws Exception {
    String javaHome = System.getProperty("java.home");
    javaBin = javaHome + File.separator + "bin" + File.separator + "java";
    classPath = System.getProperty("java.class.path");
    processBuilder = new ProcessBuilder();
  }

  @Test
  public void start() throws Exception {
    processBuilder.command(javaBin, "-cp", classPath, launcher, "start");
    Process p = processBuilder.start();
    p.waitFor();
    assertThat(p.exitValue()).isEqualTo(ExitCode.ERR_NOT_INSTALLED);
  }

}
