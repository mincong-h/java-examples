package io.mincongh.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Stack Overflow: {@link java FileUtils.delete()} not deleting folder.
 *
 * @author Mincong Huang
 */
class So42851727Test {

  @TempDir Path tempDir;

  @Test
  void testDeleteDirectoryIfExists() throws IOException {
    File myDir = Files.createDirectory(tempDir.resolve("myDir")).toFile();
    assertThat(myDir).exists();
    deleteDirectory(myDir);
    assertThat(myDir).doesNotExist();
  }

  private static void deleteDirectory(File directory) throws IOException {
    if (directory.exists()) {
      FileUtils.deleteDirectory(directory);
    }
  }
}
