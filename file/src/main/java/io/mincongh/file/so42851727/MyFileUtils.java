package io.mincongh.file.so42851727;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 * @author Mincong Huang
 */
public class MyFileUtils {

  private MyFileUtils() {
    // Utility class, do not instantiate
  }

  public static void deleteDirectory(File directory) throws IOException {
    if (directory.exists()) {
      FileUtils.deleteDirectory(directory);
    }
  }

}
