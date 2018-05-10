package io.mincong.ocpjp.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * File helper, copied from book <i>OCP Java SE 7, Programmer II,
 * Mala Gupta</i>, §7.3.3 (page 478).
 * <p>
 * The content is modified.
 *
 * @author Mala Gupta
 */
public final class FileHelper {

  private FileHelper() {
    // Utility class, do not instantiate
  }

  /**
   * Copies file content of the input file to the output file by
   * single byte.
   */
  static void copyByByte(File inputFile, File outputFile) throws IOException {
    try (
        FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out = new FileOutputStream(outputFile)
    ) {
      // Declares variable to store a single byte of data
      int b;

      // Loops until end of steam is reached
      // (no more bytes can be read)
      while ((b = in.read()) != -1) {
        // write byte data to destination file
        out.write(b);
      }
    }
  }

  /**
   * Copies file content of the input file to the output file by
   * multi-bytes (1024).
   * <p>
   * I/O operations that require reading and writing of a single byte
   * from and to a file are a costly affair. To optimize these
   * operations, we can use a byte array {@code byte[]}.
   */
  static void copyByBytes(File inputFile, File outputFile) throws IOException {
    try (
        FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out = new FileOutputStream(outputFile)
    ) {
      int len;
      byte[] byteArr = new byte[1024];

      // Unlike read(), read(byte[]) doesn't return the read bytes,
      // it returns the length of bytes read, or -1 if no more data
      // can be read. The actual data read is stored in the byteArr
      while ((len = in.read(byteArr)) != -1) {
        // write byte data to destination file
        out.write(byteArr, 0, len);
      }
    }
  }

  /**
   * Buffering stores data in memory before sending a read or write
   * request to the underlying I/O devices. buffering drastically
   * reduces the time required for performing reading and writing I/O
   * operations.
   * <p>
   * The exam might test you on how to instantiate buffered streams
   * correctly. To instantiate {@link BufferedInputStream}, you must
   * pass it an object of {@link java.io.InputStream}. To instantiate
   * {@link BufferedOutputStream}, you must pass it an object of
   * {@link java.io.OutputStream}.
   */
  static void bufferedCopyByByte(File inputFile, File outputFile) throws IOException {
    try (
        FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out = new FileOutputStream(outputFile);
        BufferedInputStream bis = new BufferedInputStream(in);
        BufferedOutputStream bos = new BufferedOutputStream(out)
    ) {
      int b;
      while ((b = bis.read()) != -1) {
        bos.write(b);
      }
    }
  }

  static void bufferedCopyByBytes(File inputFile, File outputFile) throws IOException {
    try (
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(outputFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        BufferedOutputStream bos = new BufferedOutputStream(fos)
    ) {
      int len;
      byte[] byteArr = new byte[1024];
      while ((len = bis.read(byteArr)) != -1) {
        bos.write(byteArr, 0, len);
      }
    }
  }

  @SuppressWarnings("unchecked")
  static <T> T read(File inputFile, Class<T> type)
      throws IOException, ClassNotFoundException {
    try (
        FileInputStream fis = new FileInputStream(inputFile);
        ObjectInputStream ois = new ObjectInputStream(fis)
    ) {
      return (T) ois.readObject();
    }
  }

  static void write(File outputFile, Serializable s) throws IOException {
    try (
        FileOutputStream fos = new FileOutputStream(outputFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos)
    ) {
      oos.writeObject(s);
    }
  }

}
