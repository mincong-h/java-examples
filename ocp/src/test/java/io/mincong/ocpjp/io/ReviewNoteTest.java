package io.mincong.ocpjp.io;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.assertj.core.data.Percentage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * This section lists the main points covered in this chapter.
 *
 * <p>
 * Working with class {@link File}:
 * <ul>
 * <li>{@link File} is an abstract representation of a path to a file
 * or a directory.
 * <li>You can use an object of class {@link File} to create a new
 * file or directory, delete it, or inquire about or modify its
 * attributes.
 * <li>A {@link File} instance might not be necessarily associated
 * with an actual file or directory.
 * <li>{@link File#isFile()} returns true if the path it refers to is
 * a directory.
 * <li>{@link File#isDirectory()} returns true if the path it refers
 * to is a (regular) file.
 * <li>For a directory, {@link File#list()} returns an array of
 * subdirectories and files as string. You can use method
 * {@link File#list(java.io.FilenameFilter)} to filter the listing
 * result.
 * <li>For a directory, {@link File#listFiles()} returns an array of
 * subdirectories and files as {@link File}. You can use method
 * {@link File#listFiles(java.io.FilenameFilter)} to filter the
 * listing result.
 * <li>You can create a {@link File} instance that represents a
 * nonexistent file on your file system. And you can even invoke
 * methods like {@link File#isFile()} without getting an exception.
 * <li>The objects of class {@link File} are immutable; the pathname
 * represented by a {@link File} object cannot be changed.
 * <li>Methods {@link File#createNewFile()}, {@link File#mkdir()},
 * and {@link File#mkdirs()} can be used to create new files or
 * directories.
 * </ul>
 *
 * <p>
 * Using byte steam I/O:
 * <ul>
 * <li>Class {@link InputStream} is an abstract base class for all
 * the input streams.
 * <li>Class {@link InputStream} defines multiple overloaded versions
 * of method {@link InputStream#read()}, which can be used to read a
 * single byte of data as <tt>int</tt>, or multiple bytes into a byte
 * array {@code byte[]}.
 * <li>Method {@link InputStream#read()} returns the next byte of
 * data, or <tt>-1</tt> if the end of the stream is reached. It does
 * not throw an {@link java.io.EOFException}.
 * <li>Method {@link InputStream#close()} is another important
 * method. Calling it on a stream releases the system resources
 * associated with it.
 * <li>Class {@link OutputStream} is an abstract class. It's the base
 * class for all the output streams in Java.
 * <li>The most important method of {@link OutputStream} is "write",
 * which can be used to write a single byte of data or multiple bytes
 * from a byte array to a data destination.
 * <li>Class {@link OutputStream} also defines methods "write",
 * "flush", and "close". So these are valid methods that can be
 * called on any objects of classes that extends class {@link
 * OutputStream}
 * <li>All the classes that include {@link OutputStream} in their
 * name—{@link FileOutputStream}, {@link ObjectOutputStream}, {@link
 * BufferedOutputStream} and {@link DataOutputStream}—extend abstract
 * class {@link OutputStream}, directly or indirectly.
 * <li>To read and write raw bytes from and to a file, use
 * {@link FileInputStream} and {@link FileOutputStream}.
 * <li>{@link FileInputStream} is instantiated by passing it a {@link
 * File} instance or string value. It cannot be instantiated by
 * passing it another {@link InputStream}.
 * <li>Instantiation of {@link FileOutputStream} creates a stream to
 * write to a file specified either as a {@link File} instance of a
 * string value. You can also pass a {@link boolean} value specifying
 * whether to append to the existing file contents.
 * <li>Copying a file's content might not copy its attributes. To
 * copy a file, it's advisable to use methods such as {@link
 * Files#copy(java.nio.file.Path, OutputStream)}.
 * <li>I/O operations that require reading and writing of a single
 * byte from and to a file are a <b>costly</b> affair. To optimize
 * the operation, you can use a byte array.
 * <li>Unlike {@link InputStream#read()}, {@link
 * InputStream#read(byte[])} does not return the read bytes. It
 * returns the count of bytes read, or <tt>-1</tt> if no more data
 * can be read. The actual data is read in the byte array that is
 * passed to it as a method parameter.
 * <li>Method {@link OutputStream#write(int)} writes a byte to the
 * underlying output stream. If you write an <tt>int</tt> value by
 * using this method, only the 8 low-order bits are written ot the
 * output steam; the rest are ignored.
 * <li>To buffer data with byte streams, you need classes {@link
 * BufferedInputStream} and {@link BufferedOutputStream}.
 * <li>You can instantiate a {@link BufferedInputStream} by passing
 * it an {@link InputStream} instance.
 * <li>You can specify a buffer size or use the default size for both
 * {@link BufferedInputStream} and {@link BufferedOutputStream}.
 * <li>To instantiate {@link BufferedInputStream}, you must pass it
 * an object of {@link InputStream}. To instantiate {@link
 * BufferedOutputStream}, you must pass it an object of {@link
 * OutputStream}.
 * <li>You can use {@link FileInputStream} and
 * {@link FileOutputStream} to read and write only byte data from and
 * to an underlying file. These classes ({@link FileInputStream} and
 * {@link FileOutputStream} don't define methods to work with any
 * other specific primitive data types or objects.
 * <li>Data input and output streams let you read and write primitive
 * values and strings from and to an underlying I/O stream in a
 * machine-independent way. Data written with {@link
 * DataOutputStream} can be read by {@link DataInputStream}.
 * <li>If a mismatch occurs in the type of data written by {@link
 * DataOutputStream} and the type of data read by {@link
 * DataInputStream}, you might not get a runtime exception. Because
 * data streams read and write bytes, the read operation constructs
 * the requested data from the available bytes, though incorrectly.
 * <li>An {@link ObjectOutputStream} can write primitive values and
 * objects to an {@link OutputStream}, which can by read by an {@link
 * ObjectInputStream}.
 * <li>To write objects to a file, their classes should implement
 * {@link Serializable}, or the code will throw a {@link
 * java.io.NotSerializableException}.
 * <li>If a class implements the {@link Serializable} interface, but
 * its base class doesn't, the class's instance can be serialized.
 * <li>A class whose object fields don't implement the {@link
 * Serializable} interface can't be serialized even though the class
 * itself implements the {@link Serializable} interface. An attempt
 * to serialize such object fields will throw a runtime exception.
 * <li>Retrieve the data (primitive and objects) in the order it was
 * written using object streams, or it might throw a runtime
 * exception.
 * <li>When you write objects to a file using {@link
 * ObjectOutputStream}, its <tt>transient</tt> or <tt>static</tt>
 * variables aren't written to the file.
 * </ul>
 *
 * <p>
 * Using character I/O with readers and writers
 * <ul>
 * <li>{@link Reader} and {@link Writer} are abstract base classes
 * for reading and writing Unicode-compliant character data.
 * <li>Classes {@link Reader} and {@link Writer} handle 16-bit
 * Unicode well, which isn't supported by the byte-oriented {@link
 * InputStream} and {@link OutputStream} classes.
 * <li>Abstract class {@link Reader} defines overloaded <tt>read</tt>
 * methods to read character data from an underlying data stream.
 * <li>Class {@link Reader} implements {@link java.io.Closeable} (and
 * its parent interface {@link AutoCloseable}.) So {@link Reader}
 * objects can be declared as resources with a try-with-resources
 * statement.
 * <li>Compare the overloaded <tt>read()</tt> methods of class {@link
 * InputStream} with the <tt>read()</tt> methods of class {@link
 * Reader}. The <tt>read()</tt> methods of {@link InputStream} accept
 * an array of <tt>byte</tt> as their method parameter, and the
 * <tt>read()</tt> methods of {@link Reader} accept an array of
 * <tt>char</tt> as their method parameter.
 * <li>Abstract class {@link Writer} defines overloaded
 * <tt>write()</tt> methods to write character data to an underlying
 * data source.
 * <li>With the overloaded <tt>write()</tt> methods of class {@link
 * Writer}, you can write a single character or multiple characters
 * stored in char arrays or string to a data source.
 * <li>{@link FileReader} and {@link FileWriter} are convenience
 * classes for reading and writing character data from files.
 * <li>You can instantiate a {@link FileReader} by passing it the
 * name of a file as a string value or as a {@link File} instance.
 * <li>You can instantiate a {@link FileWriter} by passing it the
 * name of a file as a string value or as a {@link File} instance.
 * You also have the options of specifying whether you want to
 * override the existing content of a file or append new content to
 * it by passing a <tt>boolean</tt> value to the constructor.
 * <li>To buffer data with character streams, you need classes {@link
 * BufferedReader} and {@link BufferedWriter}.
 * <li>You can instantiate a {@link BufferedReader} by passing it a
 * {@link Reader} instance.
 * <li>You can instantiate a {@link BufferedWriter} by passing it a
 * {@link Writer} instance.
 * <li>You can also specify a buffer size or use the default size for
 * both {@link BufferedReader} and {@link BufferedWriter}.
 * </ul>
 *
 * <p>Working with the console
 * <ul>
 * <li>Class {@link java.io.Console} defines methods to access the
 * character-based console device associated with the current JVM.
 * <li>You may or may not be able to access the console associated
 * with a JVM, depending on the underlying platform and how the JVM
 * was started.
 * <li>If you invoke a JVM from the command line without redirecting
 * the standard input and output streams, you'll be able to access
 * its console, which will typically be connected to the keyboard and
 * display from which the virtual machine was launched.
 * <li>You may not be able to access the console associated with a
 * JVM if it started automatically as a result of the execution of
 * some other program.
 * <li>You will not get access to the console when using IDEs like
 * Eclipse.
 * <li>You can access an object of class {@link java.io.Console} by
 * calling {@link System#console()}.
 * <li>If no console device is available, {@link System#console()}
 * returns <tt>null</tt>. A <tt>null</tt> value signals that either
 * the program was launched in a non-interactive environment or
 * perhaps the underlying operating system doesn't support the
 * console operations.
 * <li>You can't create an object of {@link java.io.Console}
 * yourself. Class {@link java.io.Console} doesn't define a public
 * constructor.
 * </ul>
 *
 * @author Mala Gupta
 * @author Mincong Huang
 */
public class ReviewNoteTest {

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  private File source, target;

  private File temp;

  private File dirA;

  @Before
  public void setUp() throws Exception {
    source = tempFolder.newFile("source");
    target = tempFolder.newFile("target");
    temp = tempFolder.newFile("temp");
    dirA = tempFolder.newFolder("dirA");

    Files.write(source.toPath(), Arrays.asList("L1", "L2"), UTF_8);
  }

  /* Working with class `java.io.File` */

  @Test
  public void createNewFile() throws Exception {
    File root = tempFolder.getRoot();
    File file = new File(root, "b");
    boolean isCreated = file.createNewFile();
    assertThat(isCreated).isTrue();
  }

  @Test
  public void createNewDirectory() throws Exception {
    File root = tempFolder.getRoot();
    assertThat(new File(root, "dirB").mkdir()).isTrue();
    assertThat(new File(root, "c/d/e").mkdirs()).isTrue();
  }

  @Test
  public void isDirectory() throws Exception {
    assertThat(dirA.isDirectory()).isTrue();
    assertThat(source.isDirectory()).isFalse();
  }

  @Test
  public void isFile() throws Exception {
    assertThat(source.isFile()).isTrue();
    assertThat(dirA.isFile()).isFalse();
  }

  @Test
  public void list() throws Exception {
    String[] files;

    files = tempFolder.getRoot().list();
    assertThat(files).containsExactlyInAnyOrder("source", "target", "temp", "dirA");

    files = tempFolder.getRoot().list((dir, name) -> name.startsWith("s"));
    assertThat(files).containsExactly("source");
  }

  @Test
  public void listFiles() throws Exception {
    File[] files;

    files = tempFolder.getRoot().listFiles();
    assertThat(files).containsExactlyInAnyOrder(source, target, temp, dirA);

    files = tempFolder.getRoot().listFiles((dir, name) -> name.startsWith("s"));
    assertThat(files).containsExactly(source);
  }

  @Test
  public void nonexistentFile() throws Exception {
    File f = new File("nonexistent");
    assertThat(f.exists()).isFalse();
    assertThat(f.isFile()).isFalse(); // No exception
  }

  /* Using byte steam I/O */

  @Test
  public void readFromInputStream_singleByte() throws Exception {
    try (InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(target)) {
      int data;
      while ((data = in.read()) != -1) {
        out.write(data);
      }
    }
    assertThat(Files.readAllLines(target.toPath())).containsExactly("L1", "L2");
  }

  @Test
  public void readFromInputStream_byteArray() throws Exception {
    try (InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(target)) {
      byte[] data = new byte[1024];
      int len;
      while ((len = in.read(data)) != -1) {
        out.write(data, 0, len);
      }
    }
    assertThat(Files.readAllLines(target.toPath())).containsExactly("L1", "L2");
  }

  @Test
  public void writeToOutputStream_writeMode() throws Exception {
    try (OutputStream out = new FileOutputStream(source, false)) {
      byte[] bytes = "L0".getBytes(UTF_8);
      out.write(bytes);
    }
    List<String> content = Files.readAllLines(source.toPath());
    assertThat(content).containsExactly("L0");
    assertThat(content).doesNotContain("L1", "L2");
  }

  @Test
  public void writeToOutputStream_appendMode() throws Exception {
    try (OutputStream out = new FileOutputStream(source, true)) {
      byte[] bytes = "L3".getBytes(UTF_8);
      out.write(bytes);
    }
    List<String> content = Files.readAllLines(source.toPath());
    assertThat(content).containsExactly("L1", "L2", "L3");
  }

  @Test
  public void copyFileContent() throws Exception {
    Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    assertThat(Files.readAllLines(target.toPath())).containsExactly("L1", "L2");
  }

  @Test
  public void newBufferedStream() throws Exception {
    try (
        InputStream fis = new FileInputStream(source);
        InputStream bis = new BufferedInputStream(fis);
        OutputStream fos = new FileOutputStream(target);
        OutputStream bos = new BufferedOutputStream(fos)
    ) {
      int data;
      while ((data = bis.read()) != -1) {
        bos.write(data);
      }
    }
    assertThat(Files.readAllLines(target.toPath())).containsExactly("L1", "L2");
  }

  @Test
  public void newBufferedStream_bufferSize() throws Exception {
    int bufferSize = 100;
    try (
        InputStream fis = new FileInputStream(source);
        InputStream bis = new BufferedInputStream(fis, bufferSize);
        OutputStream fos = new FileOutputStream(target);
        OutputStream bos = new BufferedOutputStream(fos, bufferSize)
    ) {
      int data;
      while ((data = bis.read()) != -1) {
        bos.write(data);
      }
    }
    assertThat(Files.readAllLines(target.toPath())).containsExactly("L1", "L2");
  }

  @Test
  public void dataStreams_primitiveValues() throws Exception {
    try (
        FileOutputStream fos = new FileOutputStream(temp);
        DataOutputStream dos = new DataOutputStream(fos)
    ) {
      dos.writeChars("Hi");
      dos.writeChar('.');
      dos.writeBoolean(true);
      dos.writeDouble(4.2F);
    }
    try (
        FileInputStream fis = new FileInputStream(temp);
        DataInputStream dis = new DataInputStream(fis)
    ) {
      assertThat(dis.readChar()).isEqualTo('H');
      assertThat(dis.readChar()).isEqualTo('i');
      assertThat(dis.readChar()).isEqualTo('.');
      assertThat(dis.readBoolean()).isTrue();
      assertThat(dis.readDouble()).isCloseTo(4.2F, Percentage.withPercentage(1));
    }
  }

  @Test
  public void objectStreams_primitiveValues() throws Exception {
    try (
        FileOutputStream fos = new FileOutputStream(temp);
        ObjectOutputStream oos = new ObjectOutputStream(fos)
    ) {
      oos.writeChar('.');
      oos.writeBoolean(true);
      oos.writeDouble(4.2F);
    }
    try (
        FileInputStream fis = new FileInputStream(temp);
        ObjectInputStream ois = new ObjectInputStream(fis)
    ) {
      assertThat(ois.readChar()).isEqualTo('.');
      assertThat(ois.readBoolean()).isTrue();
      assertThat(ois.readDouble()).isCloseTo(4.2F, Percentage.withPercentage(1));
    }
  }

  @Test
  public void objectStreams_objectValues() throws Exception {
    try (
        FileOutputStream fos = new FileOutputStream(temp);
        ObjectOutputStream oos = new ObjectOutputStream(fos)
    ) {
      oos.writeObject(new Person("Foo"));
      oos.writeObject(new Person("Bar"));
    }
    try (
        FileInputStream fis = new FileInputStream(temp);
        ObjectInputStream ois = new ObjectInputStream(fis)
    ) {
      assertThat(ois.readObject()).isEqualTo(new Person("Foo"));
      assertThat(ois.readObject()).isEqualTo(new Person("Bar"));
    }
  }

  /* Using character I/O with readers and writers */

  @Test
  public void readChars() throws Exception {
    try (Reader in = new FileReader(source)) {
      testReader(in);
    }
  }

  @Test
  public void bufferedReadChars() throws Exception {
    try (Reader r = new FileReader(source); BufferedReader br = new BufferedReader(r)) {
      testReader(br);
    }
  }

  private void testReader(Reader reader) throws IOException {
    char[] arr = new char[1024];
    int len = reader.read(arr);
    char[] chars = Arrays.copyOfRange(arr, 0, len);
    String expected = String.format(Locale.ROOT, "L1%nL2%n");
    assertThat(new String(chars)).isEqualTo(expected);
  }

  @Test
  public void writeChars() throws Exception {
    try (Writer out = new FileWriter(target)) {
      out.write(new char[]{'H', 'e', 'l', 'l', 'o'});
    }
    List<String> results = Files.readAllLines(target.toPath());
    assertThat(results).containsExactly("Hello");
  }

  @Test
  public void bufferedWriteChars() throws Exception {
    try (Writer w = new FileWriter(target); BufferedWriter bw = new BufferedWriter(w)) {
      bw.write(new char[]{'H', 'e', 'l', 'l', 'o'});
    }
    List<String> results = Files.readAllLines(target.toPath());
    assertThat(results).containsExactly("Hello");
  }

  @Test
  public void console() throws Exception {
    assumeTrue(System.console() != null);
    // Do more things here...
  }

  /* Utility classes */

  private static class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    Person(String name) {
      this.name = name;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Person)) {
        return false;
      }
      Person person = (Person) o;
      return name != null ? name.equals(person.name) : person.name == null;
    }

    @Override
    public int hashCode() {
      return name != null ? name.hashCode() : 0;
    }
  }

}
