package io.mincong.ocpjp.io;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

/**
 * @author Mincong Huang
 */
public class FileCountTest {

  @Rule
  public final TemporaryFolder temporaryDir = new TemporaryFolder();

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void countRegularFiles_correct() throws Exception {
    // Depth 1
    temporaryDir.newFile("a.txt");
    temporaryDir.newFile("b.txt");
    temporaryDir.newFile("c.txt");
    temporaryDir.newFolder("sub");

    // Depth 2
    temporaryDir.newFile("sub/d.txt");
    temporaryDir.newFile("sub/e.txt");

    // Only regular files in depth 1 are counted.
    int nbRegularFiles = FileCounter.countRegularFiles(temporaryDir.getRoot());
    assertThat(nbRegularFiles).isEqualTo(3);
  }

  @Test(expected = NullPointerException.class)
  public void countRegularFiles_nullDir() throws Exception {
    FileCounter.countRegularFiles(null);
  }

  @Test
  public void countRegularFiles_invalidDir() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Not a directory");

    FileCounter.countRegularFiles(temporaryDir.newFile("foo"));
  }

  @Test
  public void countDirectories_correct() throws Exception {
    // Depth 1
    temporaryDir.newFile("a.txt");
    temporaryDir.newFile("b.txt");
    temporaryDir.newFile("c.txt");
    temporaryDir.newFolder("sub");

    // Depth 2
    temporaryDir.newFile("sub/d.txt");
    temporaryDir.newFile("sub/e.txt");
    temporaryDir.newFolder("sub", "sub");

    // Only directories in depth 1 are counted.
    int nbDirectories = FileCounter.countDirectories(temporaryDir.getRoot());
    assertThat(nbDirectories).isEqualTo(1);
  }

  @Test(expected = NullPointerException.class)
  public void countDirectories_nullDir() throws Exception {
    FileCounter.countRegularFiles(null);
  }

  @Test
  public void countDirectories_invalidDir() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Not a directory");

    FileCounter.countRegularFiles(temporaryDir.newFile("foo"));
  }

}
