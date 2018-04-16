package io.mincongh.io;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Stack Overflow: {@link java FileUtils.delete()} not deleting folder.
 *
 * @author Mincong Huang
 */
public class So42851727Test {

  @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Test
  public void testDeleteDirectoryIfExists() throws IOException {
    File myDir = temporaryFolder.newFolder("myDir");
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
