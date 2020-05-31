package io.mincongh.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests File NIO 2
 *
 * @author Mincong Huang
 */
class PathTest {

  @TempDir Path root;

  @Test
  void containsPath() throws Exception {
    Path foo = Files.createFile(root.resolve("foo"));
    assertThat(Files.list(root)).containsExactly(foo);
  }

  @Test
  void write_truncateExisting() throws Exception {
    Path foo = root.resolve("foo");
    Files.write(foo, Arrays.asList("1", "2"), StandardOpenOption.CREATE);
    assertThat(Files.readAllLines(foo)).containsExactly("1", "2");

    Files.write(foo, Arrays.asList("3", "4"), StandardOpenOption.TRUNCATE_EXISTING);
    assertThat(Files.readAllLines(foo)).containsExactly("3", "4");
  }

  @Test
  void write_append() throws Exception {
    Path foo = root.resolve("foo");
    Files.write(foo, Arrays.asList("1", "2"), StandardOpenOption.CREATE);
    assertThat(Files.readAllLines(foo)).containsExactly("1", "2");

    Files.write(foo, Arrays.asList("3", "4"), StandardOpenOption.APPEND);
    assertThat(Files.readAllLines(foo)).containsExactly("1", "2", "3", "4");
  }

  @Test
  void move_optionReplaceExiting_targetExists() throws Exception {
    // Given an existing source file and an existing target file
    Path source = root.resolve("source");
    Path target = root.resolve("target");
    Files.write(source, Arrays.asList("S1", "S2"));
    Files.write(target, Arrays.asList("T1", "T2"));

    // When moving the source file to target file
    Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);

    // Then the target file is replaced
    assertThat(Files.readAllLines(target)).containsExactly("S1", "S2");
  }

  @Test
  void move_optionReplaceExiting_targetDoesNotExist() throws Exception {
    // Given an existing source file and a non-existent target file
    Path source = root.resolve("source");
    Path target = root.resolve("target");
    Files.write(source, Arrays.asList("S1", "S2"));

    // When moving the source file to target file
    Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);

    // Then the target file is replaced
    assertThat(Files.readAllLines(target)).containsExactly("S1", "S2");
  }

  @Test
  void readOnly() throws Exception {
    Path foo = root.resolve("foo");
    Files.createFile(foo);
    assertThat(foo).exists();

    if (Files.getFileStore(foo).supportsFileAttributeView(DosFileAttributeView.class)) {
      Files.getFileAttributeView(foo, DosFileAttributeView.class) //
          .setReadOnly(true);
    } else {
      Files.getFileAttributeView(foo, PosixFileAttributeView.class) //
          .setPermissions(PosixFilePermissions.fromString("r--r-----"));
    }
    assertThat(foo.toFile().canRead()).isTrue();
    //    assertThat(foo.toFile().canWrite()).isFalse(); // bug in OpenJDK 11 (Linux)
    assertThat(foo.toFile().canExecute()).isFalse();
  }

  @Test
  void readOnly2() throws Exception {
    File foo = root.resolve("foo").toFile();
    boolean isCreated = foo.createNewFile();
    boolean isReadOnly = foo.setReadOnly();

    assertThat(isCreated).isTrue();
    assertThat(isReadOnly).isTrue();
    assertThat(foo).exists();
  }

  @Test
  void setPosixFilePermissions() throws Exception {
    Path foo = root.resolve("foo");
    Files.createFile(foo);

    Files.setPosixFilePermissions(foo, PosixFilePermissions.fromString("rwxr-xr-x"));
    assertThat(foo.toFile().canRead()).isTrue();
    assertThat(foo.toFile().canWrite()).isTrue();
    assertThat(foo.toFile().canExecute()).isTrue();
  }

  @Test
  void newDirectoryStream() throws Exception {
    createFiles();
    List<Path> paths = new ArrayList<>();
    Files.newDirectoryStream(root).iterator().forEachRemaining(paths::add);
    assertThat(paths)
        .flatExtracting(this::getName)
        .containsOnly("file1.txt", "file2.txt", "file2.md", "sub");
  }

  @Test
  void newDirectoryStream_globFilter() throws Exception {
    createFiles();
    List<Path> paths = new ArrayList<>();

    // *.txt
    Files.newDirectoryStream(root, "*.txt").iterator().forEachRemaining(paths::add);
    assertThat(paths).flatExtracting(this::getName).containsOnly("file1.txt", "file2.txt");

    paths.clear();
    // *.md
    Files.newDirectoryStream(root, "*.md").iterator().forEachRemaining(paths::add);
    assertThat(paths).flatExtracting(this::getName).containsOnly("file2.md");
  }

  @Test
  void newDirectoryStream_lambdaFilter() throws Exception {
    createFiles();
    List<Path> paths = new ArrayList<>();

    // *.txt
    Filter<Path> filter = p -> p.getFileName().toString().endsWith(".txt");
    Files.newDirectoryStream(root, filter).iterator().forEachRemaining(paths::add);
    assertThat(paths).flatExtracting(this::getName).containsOnly("file1.txt", "file2.txt");
  }

  /**
   * Create folder with the following structure for tests.
   *
   * <pre>
   * root/
   * root/file1.txt
   * root/file2.txt
   * root/file2.md
   * root/sub/file1.txt
   * root/sub/file2.md
   * </pre>
   */
  private void createFiles() throws IOException {
    Path sub = Files.createDirectory(root.resolve("sub"));
    Files.createFile(root.resolve("file1.txt"));
    Files.createFile(root.resolve("file2.txt"));
    Files.createFile(root.resolve("file2.md"));
    Files.createFile(sub.resolve("file1.txt"));
    Files.createFile(sub.resolve("file2.md"));
  }

  private String getName(Path p) {
    return p.toFile().getName();
  }
}
