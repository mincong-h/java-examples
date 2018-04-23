package io.mincongh.cli.command;

import io.mincongh.cli.option.HasDebugOption;
import io.mincongh.cli.option.HasEncryptOption;
import io.mincongh.cli.option.HasQuietOption;
import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.cli.Option;

/**
 * Encrypt command: output encrypted value for a given parameter.
 *
 * @author Mincong Huang
 */
public class EncryptCommand extends Command<List<String>>
    implements HasEncryptOption, HasDebugOption, HasQuietOption {

  public EncryptCommand(String... args) {
    super(args);
  }

  @Override
  String name() {
    return Commands.ENCRYPT;
  }

  @Override
  void validate() {
    // Do nothing
  }

  @Override
  public Iterable<Option> newOptions() {
    return Arrays.asList(newEncryptOption(), newDebugOption(), newQuietOption());
  }

  /** @return a list of encrypted values */
  @Override
  public List<String> call() {
    String[] values = cmd.getArgs();
    List<String> results = new ArrayList<>();

    if (values.length == 0) {
      Console console = System.console();
      String v;
      if (console != null) {
        v = new String(console.readPassword("Please enter the value to encrypt: "));
      } else { // try reading from stdin
        v = new Scanner(System.in).nextLine();
      }
      results.add(encrypt(v));
    } else {
      for (String v : values) {
        results.add(encrypt(v));
      }
    }
    return results;
  }

  // TODO Use real crypto algorithm
  private String encrypt(String v) {
    return hasEncrypt() ? getEncryptAlgorithm() + v : v;
  }
}
