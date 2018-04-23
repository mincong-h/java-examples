package io.mincongh.cli.option;

import io.mincongh.cli.Messages;
import org.apache.commons.cli.Option;

/**
 * Activate encryption on the config command. This option can be used on the encrypt command to
 * customize the encryption algorithm. <algorithm> is a cipher transformation of the form:
 * "algorithm/mode/padding" or "algorithm". Default value is "AES/ECB/PKCS5Padding" (Advanced
 * Encryption Standard, Electronic Cookbook Mode, PKCS5-style padding).
 *
 * @author Mincong Huang
 */
public interface HasEncryptOption extends HasOption {

  default boolean hasEncrypt() {
    return getCommandLine().hasOption("encrypt");
  }

  default String getEncryptAlgorithm() {
    return getCommandLine().getOptionValue("encrypt");
  }

  default Option newEncryptOption() {
    return Option.builder() //
        .longOpt("encrypt") //
        .hasArg() //
        .argName("algorithm") //
        .desc(Messages.getOptionDesc("encrypt")) //
        .build();
  }
}
