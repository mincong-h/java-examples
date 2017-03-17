package io.mincongh.file.so42851727;


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Mincong Huang
 */
public class MyFileUtilsTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Test
  public void testDeleteDirectoryIfExists() throws IOException {
    File myDir = temporaryFolder.newFolder("myDir");
    assertTrue(myDir.exists());
    MyFileUtils.deleteDirectory(myDir);
    assertTrue(!myDir.exists());
  }

}
