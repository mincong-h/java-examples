package io.mincongh.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class UnzipCommandTest {
  @Rule public TemporaryFolder tempDir = new TemporaryFolder();
  private Path targetDir;
  private Path sourceZip;

  @Before
  public void setUp() throws Exception {
    targetDir = tempDir.getRoot().toPath();
    sourceZip = Paths.get(this.getClass().getResource("/zip/project.zip").toURI());
  }

  @Test
  public void sourceTarget() throws Exception {
    UnzipCommand cmd =
        UnzipCommand.newBuilder() //
            .sourceZip(sourceZip)
            .targetDir(targetDir)
            .build();
    cmd.exec();

    Path file1 = targetDir.resolve("project").resolve("file1.txt");
    Path file2 = targetDir.resolve("project").resolve("sub").resolve("file2.txt");
    assertThat(Files.readAllLines(file1)).containsExactly("line1", "line2");
    assertThat(Files.readAllLines(file2)).containsExactly("line1", "line2");
  }

  @Test(expected = NullPointerException.class)
  public void sourceOnly() {
    UnzipCommand.newBuilder() //
        .sourceZip(sourceZip)
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void byteSizeNegative() {
    UnzipCommand.newBuilder() //
        .sourceZip(sourceZip)
        .targetDir(targetDir)
        .byteSize(-1)
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void byteSizeZero() {
    UnzipCommand.newBuilder() //
        .sourceZip(sourceZip)
        .targetDir(targetDir)
        .byteSize(-1)
        .build();
  }

  @Test
  public void byteSize2048() throws Exception {
    UnzipCommand cmd =
        UnzipCommand.newBuilder() //
            .sourceZip(sourceZip)
            .targetDir(targetDir)
            .byteSize(2048)
            .build();
    cmd.exec();

    Path file1 = targetDir.resolve("project").resolve("file1.txt");
    Path file2 = targetDir.resolve("project").resolve("sub").resolve("file2.txt");
    assertThat(Files.readAllLines(file1)).containsExactly("line1", "line2");
    assertThat(Files.readAllLines(file2)).containsExactly("line1", "line2");
  }
}
