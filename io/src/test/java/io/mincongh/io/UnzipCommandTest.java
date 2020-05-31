package io.mincongh.io;

import static org.assertj.core.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class UnzipCommandTest {
  @TempDir Path targetDir;
  private Path sourceZip;

  @BeforeEach
  public void setUp() throws Exception {
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

  @Test
  public void sourceOnly() {
    assertThatNullPointerException()
        .isThrownBy(
            () ->
                UnzipCommand.newBuilder() //
                    .sourceZip(sourceZip)
                    .build());
  }

  @Test
  public void byteSizeNegative() {
    assertThatIllegalArgumentException()
        .isThrownBy(
            () ->
                UnzipCommand.newBuilder()
                    .sourceZip(sourceZip)
                    .targetDir(targetDir)
                    .bufferSize(-1)
                    .build());
  }

  @Test
  public void byteSizeZero() {
    assertThatIllegalArgumentException()
        .isThrownBy(
            () ->
                UnzipCommand.newBuilder()
                    .sourceZip(sourceZip)
                    .targetDir(targetDir)
                    .bufferSize(0)
                    .build());
  }

  @Test
  public void byteSize2048() throws Exception {
    UnzipCommand cmd =
        UnzipCommand.newBuilder() //
            .sourceZip(sourceZip)
            .targetDir(targetDir)
            .bufferSize(2048)
            .build();
    cmd.exec();

    Path file1 = targetDir.resolve("project").resolve("file1.txt");
    Path file2 = targetDir.resolve("project").resolve("sub").resolve("file2.txt");
    assertThat(Files.readAllLines(file1)).containsExactly("line1", "line2");
    assertThat(Files.readAllLines(file2)).containsExactly("line1", "line2");
  }
}
