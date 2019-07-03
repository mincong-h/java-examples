package io.mincongh.commons_cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testing examples written in usage page of Apache Commons CLI.
 *
 * @author Mincong Huang
 * @since 1.0
 * @see <a href="http://commons.apache.org/proper/commons-cli/usage.html">Apache Commons CLI</a>
 */
public class UsageTest {

  /** Section: "Create the Options" */
  @Test
  public void createTheOptions() {
    // create Options object
    Options options = new Options();

    // add t option
    options.addOption("t", false, "display current time");

    // ----- Assertion -----
    assertThat(options.hasOption("t")).isTrue();
  }

  /** Section: "Parsing the command line arguments" */
  @Test
  public void parseCommandLineArguments() throws Exception {
    Options options = new Options();
    options.addOption("t", "display time");
    String[] args = {"-t", "123"};
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(options, args);
    assertThat(cmd.hasOption("t")).isTrue();
    assertThat(cmd.hasOption("f")).isFalse();
  }
}
