package io.mincong.ocpjp.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Content modified from book <i>OCP Java SE 7, Programmer II,
 * Mala Gupta</i>, §7.2.2 Creating new files and directories on your
 * physical device (page 470).
 *
 * @author Mincong Huang
 */
public class DirectoryCreationTest {

  @Rule
  public final TemporaryFolder temporaryDir = new TemporaryFolder();

  /**
   * Tests method {@link File#createNewFile()} with a nonexistent file.
   * <p>
   * Atomically creates a new, empty file named by this abstract
   * pathname if and only if a file with this name doesn't yet exist.
   */
  @Test
  public void createNewFile_nonexistentFile() throws Exception {
    File nonExistingFile = new File(temporaryDir.getRoot(), "nonexistent.txt");
    assertThat(nonExistingFile).doesNotExist();

    boolean isCreated = nonExistingFile.createNewFile();
    assertThat(isCreated).isTrue();
  }

  /**
   * Tests method {@link File#createNewFile()} with an existing file.
   *
   * @see #createNewFile_nonexistentFile
   */
  @Test
  public void createNewFile_existingFile() throws Exception {
    File file = temporaryDir.newFile("existing.txt");
    assertThat(file).exists();

    File existingFile = new File(temporaryDir.getRoot(), "existing.txt");
    assertThat(existingFile).exists();

    // Then method #createNewFile returns false because the file already exists
    boolean isCreated = existingFile.createNewFile();
    assertThat(isCreated).isFalse();
  }

  /**
   * Tests method {@link File#mkdir()} with an existing parent
   * directory.
   */
  @Test
  public void mkdir_fromExistingParentDir() throws Exception {
    File root = temporaryDir.getRoot();

    File child = new File(root, "child");
    assertThat(child).doesNotExist();
    assertThat(child.mkdir()).isTrue();
    assertThat(child).exists();
  }

  @Test
  public void mkdir_fromNonexistentParentDir() throws Exception {
    File root = temporaryDir.getRoot();

    File grandChild = new File(root, "foo/bar");
    assertThat(grandChild).doesNotExist();
    assertThat(grandChild.mkdir()).isFalse();
    assertThat(grandChild).doesNotExist();
  }

  /**
   * The method {@link File#mkdirs()} creates the directory named by
   * this abstract pathname, including any necessary but
   * <b>nonexistent</b> directories. Note that if this operation
   * fails, it may have succeeded in creating some of the necessary
   * parent directories.
   */
  @Test
  public void mkdirs_fromNonexistentParentDir() throws Exception {
    File root = temporaryDir.getRoot();

    File grandChild = new File(root, "foo/bar");
    assertThat(grandChild).doesNotExist();
    assertThat(grandChild.mkdirs()).isTrue();
    assertThat(grandChild).exists();
  }

}
