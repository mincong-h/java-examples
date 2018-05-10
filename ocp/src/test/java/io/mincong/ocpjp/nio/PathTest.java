package io.mincong.ocpjp.nio;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Mincong Huang
 */
public class PathTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private Path r;

  private Path d1;

  private Path d2;

  @Before
  public void setUp() throws Exception {
    r = temporaryFolder.getRoot().toPath();
    d1 = Files.createDirectory(r.resolve("dir1"));
    d2 = Files.createDirectory(r.resolve("dir2"));
  }

  @Test
  @Ignore("Only executable on Mincong's laptop")
  public void manipulatePaths() throws Exception {
    Path path = Paths.get("/Users/mincong/github/oracle-certification");
    System.out.println("toString():     " + path.toString());
    System.out.println("getRoot():      " + path.getRoot());
    System.out.println("getName(0):     " + path.getName(0));
    System.out.println("getName(1):     " + path.getName(1));
    System.out.println("getFileName():  " + path.getFileName());
    System.out.println("getNameCount(): " + path.getNameCount());
    System.out.println("getParent():    " + path.getParent());
    System.out.println("subpath(0, 2):  " + path.subpath(0, 2));
  }

  @Test
  public void lines() throws Exception {
    Path p = r.resolve("file");
    Files.write(p, Arrays.asList("Line 1", "Line 2", "Line 3"));
    List<String> words = Files.lines(p)
        .map(line -> line.split(" "))
        .flatMap(Arrays::stream)
        .distinct()
        .sorted()
        .collect(Collectors.toList());
    assertThat(words).containsExactly("1", "2", "3", "Line");
  }

  @Test
  public void list() throws Exception {
    Path p = r.resolve("file");
    Files.write(p, Arrays.asList("Line 1", "Line 2", "Line 3"));
    List<Path> paths = Files.list(r)
        .filter(path -> path.getFileName().toString().startsWith("dir2"))
        .collect(Collectors.toList());
    assertThat(paths).containsExactly(d2);
  }

  @Test
  public void toAbsolutePath_noFollowLinks() throws Exception {
    Files.createFile(r.resolve("target"));
    Files.createSymbolicLink(r.resolve("link"), r.resolve("target"));

    Path p = r.resolve("link").toAbsolutePath();
    assertThat(p.getFileName().toString()).isEqualTo("link");
  }

  @Test
  public void toAbsolutePath_nonexistent() throws Exception {
    Path p = r.resolve("nonexistent").toAbsolutePath(); // no exception raised
    assertThat(p.getFileName().toString()).isEqualTo("nonexistent");
  }

  @Test(expected = NoSuchFileException.class)
  public void toRealPath_nonexistent() throws Exception {
    r.resolve("nonexistent").toRealPath();
  }

  @Test
  public void toRealPath_followLinks() throws Exception {
    Files.createFile(r.resolve("target"));
    Files.createSymbolicLink(r.resolve("link"), r.resolve("target"));

    Path target = r.resolve("link").toRealPath();
    assertThat(target.getFileName().toString()).isEqualTo("target");
  }

  @Test
  public void getRoot_relativePath() throws Exception {
    assertThat(Paths.get("foo/bar").getRoot()).isNull();
  }

  @Test
  public void getRoot_absolutePath() throws Exception {
    assertThat(r.toAbsolutePath().getRoot()).isNotNull();
  }

  @Test
  public void getParent_existingParent() throws Exception {
    assertThat(d1.getParent()).isNotNull();
  }

  @Test
  public void getParent_nonexistentParent() throws Exception {
    assertThat(Paths.get("foo").getParent()).isNull();
  }

  @Test
  public void getNameCount() throws Exception {
    assertThat(Paths.get("/foo").getNameCount()).isEqualTo(1);
    assertThat(Paths.get("/foo").getName(0).toString()).isEqualTo("foo");
  }

  @Test
  public void toRealPath_noFollowLinks() throws Exception {
    Files.createFile(r.resolve("target"));
    Files.createSymbolicLink(r.resolve("link"), r.resolve("target"));

    Path link = r.resolve("link").toRealPath(LinkOption.NOFOLLOW_LINKS);
    assertThat(link.getFileName().toString()).isEqualTo("link");
  }

  @Test
  public void iterable() throws Exception {
    Path a = Paths.get("dirA/fileA");
    Path b = Paths.get("dirB/fileB");
    Path relative = a.relativize(b);

    List<String> names = new ArrayList<>();
    for (Path p : relative) {
      names.add(p.toString());
    }
    assertThat(names).containsExactly("..", "..", "dirB", "fileB");
    assertThat(relative.getName(0).getFileName().toString()).isEqualTo("..");
    assertThat(relative.getName(1).getFileName().toString()).isEqualTo("..");
    assertThat(relative.getName(2).getFileName().toString()).isEqualTo("dirB");
    assertThat(relative.getName(3).getFileName().toString()).isEqualTo("fileB");
  }

  @Test
  public void exists() throws Exception {
    assertThat(Files.exists(r)).isTrue();
    assertThat(Files.exists(d1)).isTrue();
    assertThat(Files.exists(d2)).isTrue();
  }

  @Test
  public void notExists() throws Exception {
    /*
     * Method `notExists()` is NOT complement of method `exists()`.
     * It returns `true` if a target doesn't exist. If these methods
     * cannot determine the existence of a file, both of them will
     * return `false`.
     *
     * You must access the file system to verify that a particular
     * `Path` exists, or does not exist. When you are testing a
     * file's existence, three results are possible:
     *
     * 1. The file is verified to exist.
     * 2. The file is verified to not exist.
     * 3. The file's status is unknown. This result can occur when
     *    the program does not have access to the file.
     */
    assertThat(Files.notExists(r.resolve("nonexistent"))).isTrue();
  }

  @Test
  public void resolve() throws Exception {
    Path path = r.resolve("foo");
    assertThat(path.getParent()).isEqualTo(r);
  }

  @Test
  public void resolveSibling_regularFile() throws Exception {
    // Given a filepath under directory `dir1`
    Path source = Files.createFile(d1.resolve("foo"));

    // When sibling-resolving such filepath
    Path target = source.resolveSibling("bar");

    // Then target filepath is resolved using the parent of the source filepath
    assertThat(source.getParent()).isEqualTo(d1);
    assertThat(target.getParent()).isEqualTo(d1);
  }

  @Test
  public void resolveSibling_directory() throws Exception {
    // Given a dir-path under directory `dir1`
    Path source = Files.createDirectory(d1.resolve("subA"));

    // When sibling-resolving such dir-path
    Path target = source.resolveSibling("subB");

    // Then target filepath is resolved using the parent of the source filepath
    assertThat(source.getParent()).isEqualTo(d1);
    assertThat(target.getParent()).isEqualTo(d1);
  }

  @Test
  public void relativize() throws Exception {
    Path relativeD1 = r.relativize(d1);
    assertThat(relativeD1.toString()).isEqualTo("dir1");
  }

  @Test(expected = IOException.class)
  public void createDirectory_nonexistentParent() throws Exception {
    Files.createDirectory(r.resolve("nonexistent/foo"));
  }

  @Test
  public void createDirectories() throws Exception {
    Files.createDirectories(r.resolve("a/b/c"));

    assertThat(r.resolve("a")).exists();
    assertThat(r.resolve("a/b")).exists();
    assertThat(r.resolve("a/b/c")).exists();
  }

  @Test
  public void copy() throws Exception {
    Path source = r.resolve("source");
    Files.write(source, Arrays.asList("Line 1", "Line 2", "Line 3"));

    Path target = r.resolve("target");
    assertThat(target).doesNotExist();

    Files.copy(source, target);
    assertThat(Files.readAllLines(target)).containsExactly("Line 1", "Line 2", "Line 3");
  }

  @Test(expected = FileAlreadyExistsException.class)
  public void copy_failed() throws Exception {
    Path source = r.resolve("source");
    Files.createFile(source);

    Path target = r.resolve("target");
    Files.createFile(target);

    Files.copy(source, target);
  }

  @Test
  public void copy_optionCopyAttributes() throws Exception {
    Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");

    Path target = r.resolve("target");
    Path source = r.resolve("source");
    Files.createDirectory(source);
    Files.setPosixFilePermissions(source, permissions);

    Files.copy(source, target, StandardCopyOption.COPY_ATTRIBUTES);
    assertThat(target).isDirectory();
    assertThat(Files.getPosixFilePermissions(target)).containsAll(permissions);
  }

  @Test
  public void copy_optionReplaceExisting() throws Exception {
    Path source = r.resolve("source");
    Files.write(source, Arrays.asList("1", "2", "3"));

    Path target = r.resolve("target");
    Files.write(target, Arrays.asList("A", "B", "C"));

    /*
     * Method `copy()` in class `Files` doesn't allow you to append
     * data to an existing file; rather, it creates a new file or
     * replaces an existing one.
     */
    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    assertThat(Files.readAllLines(target)).containsExactly("1", "2", "3");
  }

  @Test
  public void copy_inputStream() throws Exception {
    Path source = r.resolve("source");
    Files.write(source, Arrays.asList("1", "2", "3"));
    Path target = r.resolve("target");

    try (InputStream inputStream = new FileInputStream(source.toFile())) {
      Files.copy(inputStream, target);
    }
    assertThat(Files.readAllLines(target)).containsExactly("1", "2", "3");
  }

  @Test
  public void move_file() throws Exception {
    Path source = r.resolve("source");
    Files.write(source, Arrays.asList("1", "2", "3"));
    Path target = r.resolve("target");

    Files.move(source, target);

    assertThat(Files.readAllLines(target)).containsExactly("1", "2", "3");
    assertThat(source).doesNotExist();
  }

  @Test
  public void move_directory() throws Exception {
    Path regularFile = d1.resolve("file");
    Files.createFile(regularFile);

    Files.move(d1, d2, StandardCopyOption.REPLACE_EXISTING);

    assertThat(d1).doesNotExist();
    assertThat(d2).exists();
    assertThat(d2.resolve("file")).exists();
  }

  @Test(expected = NoSuchFileException.class)
  public void delete_nonexistentFile() throws Exception {
    Files.delete(r.resolve("nonexistent"));
  }

  @Test
  public void delete_existingFile() throws Exception {
    Path regularFile = r.resolve("file");
    Files.createFile(regularFile);
    Files.delete(regularFile);

    assertThat(r.resolve("file")).doesNotExist();
  }

  @Test
  public void delete_emptyDirectory() throws Exception {
    Files.delete(d1);
    assertThat(r.resolve("dir1")).doesNotExist();
  }

  @Test(expected = DirectoryNotEmptyException.class)
  public void delete_nonEmptyDirectory() throws Exception {
    Path regularFile = d1.resolve("file");
    Files.createFile(regularFile);
    Files.delete(d1);
  }

  @Test
  public void deleteIfExists_nonexistentFile() throws Exception {
    assertThat(Files.deleteIfExists(r.resolve("nonexistent"))).isFalse();
  }

  /* Files and directory attributes */

  @Test
  public void individualAttributes() throws Exception {
    Path path = r.resolve("file");
    Files.createFile(path);

    assertThat(Files.size(path)).isZero();
    assertThat(Files.isDirectory(path)).isFalse();
    assertThat(Files.isHidden(path)).isFalse();
    assertThat(Files.isSymbolicLink(path)).isFalse();
    assertThat(Files.isSameFile(path, r)).isFalse();

    assertThat(Files.isExecutable(path)).isFalse();
    assertThat(Files.isReadable(path)).isTrue();
    assertThat(Files.isWritable(path)).isTrue();
  }

  /**
   * Querying the file system multiple times to access all file or
   * directory attributes can affect your application's performance.
   * To get around this, you can access a group of file attributes by
   * calling <code>Files#getFileAttributeView</code> or
   * <code>Files#readAttributes</code>.
   */
  @Test
  @Ignore
  public void posixFileAttributesView() throws Exception {
    /*
     * If a file system doesn't support an attribute view,
     * `Files.getFileAttributeView()` returns `null`. If a file
     * system doesn't support an attribute set,
     * `File.readAttributes()` will throw a runtime exception.
     */
    PosixFileAttributeView view = Files.getFileAttributeView(r, PosixFileAttributeView.class);
    PosixFileAttributes attributes = view.readAttributes();

    assertThat(attributes.group().getName()).isEqualTo("staff");
    assertThat(attributes.owner().getName()).isEqualTo("mincong");
  }

  @Test
  public void basicFileAttributesView() throws Exception {
    BasicFileAttributeView view = Files.getFileAttributeView(r, BasicFileAttributeView.class);
    BasicFileAttributes attributes = view.readAttributes();

    assertThat(attributes.isDirectory()).isTrue();
    assertThat(attributes.isRegularFile()).isFalse();
    assertThat(attributes.isSymbolicLink()).isFalse();
    assertThat(attributes.size()).isPositive();
    assertThat(attributes.isOther()).isFalse();
  }

}
