package io.mincong.ocpjp.io;

import java.io.File;

/**
 * File counter, copied from book <i>OCP Java SE 7, Programmer II, Mala Gupta</i>, §7.2.1 (page
 * 470).
 *
 * <p>The content is modified.
 *
 * @author Mala Gupta
 */
public final class FileCounter {

  private FileCounter() {
    // Utility class, do not instantiate.
  }

  /**
   * Count number of regular files in the given directory (depth=1), subdirectories are excluded.
   */
  public static int countRegularFiles(File dir) {
    if (!dir.isDirectory()) {
      throw new IllegalArgumentException("Not a directory");
    }
    int count = 0;
    for (String listItem : dir.list()) {
      File item = new File(dir, listItem);
      if (item.isFile()) {
        count++;
      }
    }
    return count;
  }

  /** Count number of directories in the given directory (depth=1), subdirectories are excluded. */
  public static int countDirectories(File dir) {
    if (!dir.isDirectory()) {
      throw new IllegalArgumentException("Not a directory");
    }
    int count = 0;
    for (String listItem : dir.list()) {
      File item = new File(dir, listItem);
      if (item.isDirectory()) {
        count++;
      }
    }
    return count;
  }
}
