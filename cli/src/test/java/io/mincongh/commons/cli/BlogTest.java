package io.mincongh.commons.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for my technical blog.
 *
 * @author Mincong Huang
 * @since 1.0
 */
class BlogTest {

  @Test
  void createOptions() {
    Options options = newOptions();

    assertThat(options.hasOption("v")).isTrue();
    assertThat(options.getOption("v").hasArg()).isFalse();
    assertThat(options.getOption("v").getDescription()).isEqualTo("Enable verbose mode");

    assertThat(options.hasOption("f")).isTrue();
    assertThat(options.getOption("f").hasArg()).isTrue();
    assertThat(options.getOption("f").getDescription()).isEqualTo("Config filepath");

    assertThat(options.hasOption("i")).isTrue();
    assertThat(options.hasOption("ignore")).isTrue();
    assertThat(options.getOption("i").hasArg()).isFalse();
    assertThat(options.getOption("i").getDescription()).isEqualTo("Ignore case");

    assertThat(options.hasOption("a")).isTrue();
    assertThat(options.hasOption("all")).isTrue();
    assertThat(options.getOption("a").hasArg()).isFalse();
    assertThat(options.getOption("a").getDescription()).isEqualTo("Display all");
  }

  @Test
  void parseCommandLineArguments() {
    Options options = newOptions();

    // Parse Arguments
    CommandLineParser parser = new DefaultParser();
    String[] args = {"-v", "-f", "/path/to/file"};
    CommandLine cmd;

    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      // Parse Exception
      // ---
      // A checked exception `ParseException` is thrown if parsing failed
      throw new IllegalStateException(e);
    }

    // Query Command Line
    assertThat(cmd.getArgList()).isEmpty(); // remaining
    assertThat(cmd.hasOption("v")).isTrue();
    assertThat(cmd.hasOption("f")).isTrue();
    assertThat(cmd.hasOption("encoding")).isFalse();

    // Retrieve Arguments
    // ---
    // 1. null if not exist
    // 2. string if exist
    // 3. fall back on default value
    assertThat(cmd.getOptionValue("v")).isNull();
    assertThat(cmd.getOptionValue("f")).isEqualTo("/path/to/file");
    assertThat(cmd.getOptionValue("encoding", "utf-8")).isEqualTo("utf-8");
  }

  @Test
  void usageAndHelp() {
    // Print usage and help
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("cmd", newOptions());
    formatter.printHelp("cmd", newOptions(), true);
  }

  private static Options newOptions() {
    // Create "Options" object
    // ---
    // For more detail, check Javadoc
    Options options = new Options();

    // Create new option
    // --
    // Different ways to create a new option
    options.addOption("v", "Enable verbose mode");
    options.addOption("f", true, "Config filepath");
    options.addOption("i", "ignore", false, "Ignore case");
    options.addOption(Option.builder("a").longOpt("all").desc("Display all").build());
    return options;
  }
}
