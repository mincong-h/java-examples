package io.mincongh.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Mincong Huang
 * @since 0.1
 */
public class UnzipCommand {

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private Path targetDir;
    private Path sourceZip;
    private int byteSize = 1024;

    private Builder() {}

    /**
     * (REQUIRED) Source filepath to unzip.
     *
     * @param zip the filepath to unzip
     * @return this
     */
    public Builder sourceZip(Path zip) {
      this.sourceZip = zip;
      return this;
    }

    /**
     * (REQUIRED) Target directory where the unzipped files should be placed. The given input has to
     * be an existing directory.
     *
     * <p>Example: Unzipping "/source/foo.zip" to target directory "/target/", the results will be
     * found in directory "/target/foo/".
     *
     * @param dir existing target directory
     * @return this
     */
    public Builder targetDir(Path dir) {
      this.targetDir = dir;
      return this;
    }

    /**
     * (OPTIONAL) Byte size for the unzip buffer. The value must be positive. Default to 1024 bytes.
     *
     * @param byteSize byte size for the unzip buffer
     * @return this
     */
    public Builder bufferSize(int byteSize) {
      this.byteSize = byteSize;
      return this;
    }

    public UnzipCommand build() {
      Objects.requireNonNull(sourceZip);
      Objects.requireNonNull(targetDir);
      if (byteSize <= 0) {
        throw new IllegalArgumentException("Required positive value, but byteSize=" + byteSize);
      }
      return new UnzipCommand(this);
    }
  }

  private final int byteSize;
  private final Path sourceZip;
  private final Path targetDir;

  private UnzipCommand(Builder builder) {
    this.byteSize = builder.byteSize;
    this.sourceZip = builder.sourceZip;
    this.targetDir = builder.targetDir;
  }

  /**
   * Execute the unzip command.
   *
   * @throws IOException if any I/O error occurs
   */
  public void exec() throws IOException {
    try (InputStream is = Files.newInputStream(sourceZip);
        ZipInputStream zis = new ZipInputStream(is)) {
      ZipEntry entry = zis.getNextEntry();
      while (entry != null) {
        if (entry.isDirectory()) {
          Path dir = targetDir.resolve(entry.getName());
          Files.createDirectories(dir);
        } else {
          Path path = targetDir.resolve(entry.getName());
          try (OutputStream os = Files.newOutputStream(path)) {
            byte[] buffer = new byte[byteSize];
            int len;
            while ((len = zis.read(buffer)) > 0) {
              os.write(buffer, 0, len);
            }
          }
        }
        entry = zis.getNextEntry();
      }
      zis.closeEntry();
    }
  }
}
