package io.mincong.ocpjp.io;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Chapter 7.4 Using character I/O with readers and writers
 * <p>
 * {@link Reader} and {@link Writer} are abstract base classes for
 * reading and writing Unicode-compliant character data. They don't
 * replace the byte-oriented I/O classes, but supplement them.
 * <p>
 * Classes {@link Reader} and {@link Writer} handle 16-bit Unicode
 * well, which isn't supported by the byte-oriented
 * {@link java.io.InputStream} and {@link java.io.OutputStream}
 * classes. Also note that Java's primitive data type {@code char}
 * stores 16-bit Unicode values. Even though you can use
 * {@link java.io.InputStream} and {@link java.io.OutputStream} to
 * read and write characters, you should use the character-oriented
 * {@link Reader} and {@link Writer} classes to read and write
 * character data. Internationalization is possible only by using
 * 16-bit Unicode values. Also {@link Reader} and {@link Writer}
 * classes offer faster I/O operations.
 *
 * @author Mala Gupta
 * @author Mincong Huang
 */
public class FileReaderWriterTest {

  @Rule
  public TemporaryFolder temporaryDir = new TemporaryFolder();

  private File source;

  private File target;

  private final int size = 100_000;

  private final List<String> lines = IntStream.range(0, size)
      .mapToObj(i -> "Line " + i)
      .collect(Collectors.toList());

  @Before
  public void setUp() throws Exception {
    source = temporaryDir.newFile("source.txt");
    target = temporaryDir.newFile("target.txt");
    Files.write(source.toPath(), lines, UTF_8);
  }

  @Test
  public void readWriteByByte() throws Exception {
    try (
        Reader in = new FileReader(source);
        Writer out = new FileWriter(target)
    ) {
      int data;
      while ((data = in.read()) != -1) {
        out.write(data);
      }
    }
    List<String> results = Files.readAllLines(target.toPath());
    assertThat(results).hasSize(size);
  }

  @Test
  public void readWriteByChars() throws Exception {
    try (
        Reader in = new FileReader(source);
        Writer out = new FileWriter(target)
    ) {
      int len;
      char[] data = new char[1024];
      while ((len = in.read(data)) != -1) {
        out.write(data, 0, len);
      }
    }
    List<String> results = Files.readAllLines(target.toPath());
    assertThat(results).hasSize(size);
  }

  @Test
  public void bufferedReadWriteByByte() throws Exception {
    try (
        FileReader fr = new FileReader(source);
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter(target);
        BufferedWriter bw = new BufferedWriter(fw)
    ) {
      int data;
      while ((data = br.read()) != -1) {
        bw.write(data);
      }
    }
    List<String> results = Files.readAllLines(target.toPath());
    assertThat(results).hasSize(size);
  }

  @Test
  public void bufferedReadWriteByChars() throws Exception {
    try (
        FileReader fr = new FileReader(source);
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter(target);
        BufferedWriter bw = new BufferedWriter(fw)
    ) {
      int len;
      char[] data = new char[1024];
      while ((len = br.read(data)) != -1) {
        bw.write(data, 0, len);
      }
    }
    List<String> results = Files.readAllLines(target.toPath());
    assertThat(results).hasSize(size);
  }

  @Test
  public void bufferedReadWriteByLine() throws Exception {
    try (
        FileReader fr = new FileReader(source);
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter(target);
        BufferedWriter bw = new BufferedWriter(fw)
    ) {
      String line;
      while ((line = br.readLine()) != null) {
        /*
         * Instead of using method `read()`, it uses method
         * `readLine()` to read a single line from the character
         * stream. Method `readLine()` doesn't include line-feed '\n'
         * and carriage-return '\r' characters. So you use
         * `bw.newLine()` to insert a new line in the output file.
         */
        bw.write(line);
        bw.newLine();
      }
    }
    List<String> results = Files.readAllLines(target.toPath());
    assertThat(results).hasSize(size);
  }

}
