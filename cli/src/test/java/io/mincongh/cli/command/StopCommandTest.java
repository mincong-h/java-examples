package io.mincongh.cli.command;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests stop-command.
 *
 * @author Mincong Huang
 */
public class StopCommandTest {

  @Test
  public void getDebugCategories_debugNotDefined() {
    String[] args = {};
    StopCommand cmd = new StopCommand(args);
    assertThat(cmd.getDebugCategories()).isEmpty();
  }

  @Test(expected = IllegalStateException.class)
  public void getDebugCategories_debugZero() {
    String[] args = {"--debug"};
    new StopCommand(args);
  }

  @Test
  public void getDebugCategories_debugOne() {
    String[] args = {"--debug", "ns.A"};
    StopCommand cmd = new StopCommand(args);
    assertThat(cmd.getDebugCategories()).containsExactly("ns.A");
  }

  @Test
  public void getDebugCategories_debugTwoCombined() {
    String[] args = {"--debug", "ns.A;ns.B"};
    StopCommand cmd = new StopCommand(args);
    assertThat(cmd.getDebugCategories()).containsExactly("ns.A", "ns.B");
  }

  @Test
  public void getDebugCategories_debugTwoCombinedWithSpace() {
    String[] args = {"--debug", "ns.A; ns.B"};
    StopCommand cmd = new StopCommand(args);
    assertThat(cmd.getDebugCategories()).containsExactly("ns.A", "ns.B");
  }

  @Test
  public void getDebugCategories_debugTwoSeparated() {
    String[] args = {"--debug", "ns.A;", "ns.B"};
    StopCommand cmd = new StopCommand(args);
    String explanation = "Extra space(s) cause incorrect parsing.";
    assertThat(cmd.getDebugCategories()).as(explanation).containsExactly("ns.A", "", "ns.B");
  }

  @Test
  public void isQuiet_noOpt() {
    String[] args = {};
    StopCommand cmd = new StopCommand(args);
    assertThat(cmd.isQuiet()).isFalse();
  }

  @Test
  public void isQuiet_shortOpt() {
    String[] args = {"-q"};
    StopCommand cmd = new StopCommand(args);
    assertThat(cmd.isQuiet()).isTrue();
  }

  @Test
  public void isQuiet_longOpt() {
    String[] args = {"--quiet"};
    StopCommand cmd = new StopCommand(args);
    assertThat(cmd.isQuiet()).isTrue();
  }

  @Test
  public void hasGui_noOpt() {
    String[] args = {};
    StopCommand cmd = new StopCommand(args);
    assertThat(cmd.hasGui()).isFalse();
  }

  @Test
  public void hasGui_longOpt() {
    String[] args = {"--gui"};
    StopCommand cmd = new StopCommand(args);
    assertThat(cmd.hasGui()).isTrue();
  }
}
