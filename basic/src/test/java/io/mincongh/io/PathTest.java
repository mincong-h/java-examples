package io.mincongh.io;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {

  @Rule public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void containsPath() throws Exception {
    Path root = folder.getRoot().toPath();
    Path foo = Files.createFile(root.resolve("foo"));
    assertThat(Files.list(root)).containsExactly(foo);
  }
}
