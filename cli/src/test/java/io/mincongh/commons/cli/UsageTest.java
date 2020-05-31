package io.mincongh.commons.cli;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.cli.*;
import org.junit.jupiter.api.Test;

/**
 * Testing examples written in usage page of Apache Commons CLI.
 *
 * @author Mincong Huang
 * @since 1.0
 * @see <a href="http://commons.apache.org/proper/commons-cli/usage.html">Apache Commons CLI</a>
 */
class UsageTest {

  /** Section: "Create the Options" */
  @Test
  void createTheOptions() {
    // create Options object
    Options options = new Options();

    // add t option
    options.addOption("t", false, "display current time");

    // ----- Assertion -----
    assertThat(options.hasOption("t")).isTrue();
  }

  /** Section: "Parsing the command line arguments" */
  @Test
  void parseCommandLineArguments() throws Exception {
    Options options = new Options();
    options.addOption("t", "display time");
    String[] args = {"-t", "123"};
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(options, args);
    assertThat(cmd.hasOption("t")).isTrue();
    assertThat(cmd.hasOption("f")).isFalse();
  }

  /** Section: "Argument Options" */
  @Test
  void argumentOptions() {
    Option logfile =
        Option.builder("logfile") //
            .hasArg()
            .desc("use given file for log")
            .argName("file")
            .build();

    Option logger =
        Option.builder("logger")
            .argName("classname")
            .hasArg()
            .desc("the class which it to perform logging")
            .build();

    Option listener =
        Option.builder("listener")
            .argName("classname")
            .hasArg()
            .desc("add an instance of class as " + "a project listener")
            .build();

    Option buildfile =
        Option.builder("buildfile") //
            .argName("file")
            .hasArg()
            .desc("use given buildfile")
            .build();

    Option find =
        Option.builder("find")
            .argName("file")
            .hasArg()
            .desc("search for buildfile towards the " + "root of the filesystem and use it")
            .build();

    assertThat(logfile.getArgName()).isEqualTo("file");
    assertThat(logger.getArgName()).isEqualTo("classname");
    assertThat(listener.getArgName()).isEqualTo("classname");
    assertThat(buildfile.getArgName()).isEqualTo("file");
    assertThat(find.getArgName()).isEqualTo("file");
  }
}
