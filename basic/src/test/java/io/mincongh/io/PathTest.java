package io.mincongh.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {

  @Rule public TemporaryFolder folder = new TemporaryFolder();

  private Path root;

  @Before
  public void setUp() {
    root = folder.getRoot().toPath();
  }

  @Test
  public void containsPath() throws Exception {
    Path foo = Files.createFile(root.resolve("foo"));
    assertThat(Files.list(root)).containsExactly(foo);
  }

  @Test
  public void write_truncateExisting() throws Exception {
    Path foo = root.resolve("foo");
    Files.write(foo, Arrays.asList("1", "2"), StandardOpenOption.CREATE);
    assertThat(Files.readAllLines(foo)).containsExactly("1", "2");

    Files.write(foo, Arrays.asList("3", "4"), StandardOpenOption.TRUNCATE_EXISTING);
    assertThat(Files.readAllLines(foo)).containsExactly("3", "4");
  }

  @Test
  public void write_append() throws Exception {
    Path foo = root.resolve("foo");
    Files.write(foo, Arrays.asList("1", "2"), StandardOpenOption.CREATE);
    assertThat(Files.readAllLines(foo)).containsExactly("1", "2");

    Files.write(foo, Arrays.asList("3", "4"), StandardOpenOption.APPEND);
    assertThat(Files.readAllLines(foo)).containsExactly("1", "2", "3", "4");
  }
}
