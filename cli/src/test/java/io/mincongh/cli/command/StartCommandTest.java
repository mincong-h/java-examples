package io.mincongh.cli.command;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests start-command.
 *
 * @author Mincong Huang
 */
public class StartCommandTest {

  @Test
  public void start() {
    StartCommand cmd = new StartCommand("--debug", "ns.A;ns.B", "--strict");
    cmd.call();
  }

  // TODO Move tests to different interfaces
  @Test
  public void getDebugCategories_debugNotDefined() {
    StartCommand cmd = new StartCommand();
    assertThat(cmd.getDebugCategories()).isEmpty();
  }

  @Test(expected = IllegalStateException.class)
  public void getDebugCategories_debugZero() {
    StartCommand cmd = new StartCommand("--debug");
    assertThat(cmd.isDebug()).isTrue();
  }

  @Test
  public void getDebugCategories_debugOne() {
    StartCommand cmd = new StartCommand("--debug", "ns.A");
    assertThat(cmd.isDebug()).isTrue();
    assertThat(cmd.getDebugCategories()).containsExactly("ns.A");
  }

  @Test
  public void getDebugCategories_debugTwoCombined() {
    StartCommand cmd = new StartCommand("--debug", "ns.A;ns.B");
    assertThat(cmd.isDebug()).isTrue();
    assertThat(cmd.getDebugCategories()).containsExactly("ns.A", "ns.B");
  }

  @Test
  public void getDebugCategories_debugTwoCombinedWithSpace() {
    StartCommand cmd = new StartCommand("--debug", "ns.A; ns.B");
    assertThat(cmd.isDebug()).isTrue();
    assertThat(cmd.getDebugCategories()).containsExactly("ns.A", "ns.B");
  }

  @Test
  public void getDebugCategories_debugTwoSeparated() {
    StartCommand cmd = new StartCommand("--debug", "ns.A;", "ns.B");
    assertThat(cmd.isDebug()).isTrue();
    // Extra space(s) cause incorrect parsing.
    assertThat(cmd.getDebugCategories()).containsExactly("ns.A", "", "ns.B");
  }

  @Test
  public void isQuiet_noOpt() {
    StartCommand cmd = new StartCommand();
    assertThat(cmd.isQuiet()).isFalse();
  }

  @Test
  public void isQuiet_shortOpt() {
    StartCommand cmd = new StartCommand("-q");
    assertThat(cmd.isQuiet()).isTrue();
  }

  @Test
  public void isQuiet_longOpt() {
    StartCommand cmd = new StartCommand("--quiet");
    assertThat(cmd.isQuiet()).isTrue();
  }

  @Test
  public void hasGui_noOpt() {
    StartCommand cmd = new StartCommand();
    assertThat(cmd.hasGui()).isFalse();
  }

  @Test
  public void hasGui_longOpt() {
    StartCommand cmd = new StartCommand("--gui");
    assertThat(cmd.hasGui()).isTrue();
  }
}
