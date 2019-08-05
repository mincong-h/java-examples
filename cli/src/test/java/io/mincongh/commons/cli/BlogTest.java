package io.mincongh.commons.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for my technical blog.
 *
 * @author Mincong Huang
 * @since 1.0
 */
public class BlogTest {

  @Test
  public void createOptions() {
    // create "options"
    Options options = new Options();

    // add new option
    options.addOption("v", "Enable verbose mode");
    options.addOption("f", true, "Config filepath");
    options.addOption("i", "ignore", false, "Ignore case");
    options.addOption(Option.builder("a").longOpt("all").desc("Display all").build());

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
  public void parseCommandLineArguments() throws Exception {
    Options options = new Options();
    options.addOption("v", "Enable verbose mode");
    options.addOption("f", true, "Config filepath");

    // parse arguments
    CommandLineParser parser = new DefaultParser();
    String[] args = {"-v", "-f", "/path/to/file"};
    CommandLine cmd = parser.parse(options, args);

    // status
    assertThat(cmd.getArgList()).isEmpty(); // remaining
    assertThat(cmd.hasOption("v")).isTrue();
    assertThat(cmd.hasOption("f")).isTrue();
    assertThat(cmd.hasOption("encoding")).isFalse();

    // retrieve arguments
    assertThat(cmd.getOptionValue("v")).isNull();
    assertThat(cmd.getOptionValue("f")).isEqualTo("/path/to/file");
    assertThat(cmd.getOptionValue("encoding", "utf-8")).isEqualTo("utf-8");
  }
}
