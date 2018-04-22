package io.mincongh.cli.option;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

/**
 * Desired behaviors when having option "debug".
 *
 * @author Mincong Huang
 */
public interface HasDebugOption extends HasOption {

  /**
   * Gets a list of Java categories to debug. Each element is a full class name with package
   * namespace. The list is empty if the debug flag is not set, the list contains only "ns.Default"
   * if the debug flag is set without arguments.
   *
   * @return a list of Java categories to debug.
   */
  @NotNull
  default List<String> getDebugCategories() {
    CommandLine cmd = getCommandLine();
    if (cmd.hasOption("d")) {
      String[] values = cmd.getOptionValues("d");
      return Stream.of(values).map(String::trim).collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }

  default boolean isDebug() {
    return getCommandLine().hasOption("d");
  }

  /**
   * Creates a new debug option.
   *
   * <p>This is an internal method.
   *
   * @return a new debug option
   */
  default Option newDebugOption() {
    return Option.builder("d") //
        .longOpt("debug") //
        .hasArgs() //
        .argName("categories")
        .valueSeparator(';')
        .desc("Activate debug messages. Define Java categories to debug using comma (;).") //
        .build();
  }
}
