package io.mincongh.io;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test glob expression in file methods in Java 8.
 *
 * @author Mincong Huang
 * @since 1.0
 */
public class GlobExpressionFileTest {
  @Rule public TemporaryFolder tempFolder = new TemporaryFolder();

  private Path root;
  private Path f0;
  private Path b0;
  private Path d1;
  private Path f1;
  private Path d2;
  private Path f2;

  /**
   * Setup a directory structure with the following content:
   *
   * <pre>
   * .
   * ├── foo0.txt
   * ├── bar0.txt
   * ├── sub1
   * │   └── foo1.txt
   * └── sub2
   *     └── foo2.txt
   * </pre>
   */
  @Before
  public void setUp() throws Exception {
    root = tempFolder.getRoot().toPath();
    f0 = tempFolder.newFile("foo0.txt").toPath();
    b0 = tempFolder.newFile("bar0.txt").toPath();
    d1 = tempFolder.newFolder("sub1").toPath();
    f1 = Files.createFile(d1.resolve("foo1.txt"));
    d2 = tempFolder.newFolder("sub2").toPath();
    f2 = Files.createFile(d2.resolve("foo2.txt"));
  }

  /**
   * Method {@link Files#newDirectoryStream(Path, String)} iterate the given directory path.
   * However, it will NOT iterate recursively the files in sub-directories.
   */
  @Test
  public void newDirectoryStream() throws Exception {
    try (DirectoryStream<Path> paths = Files.newDirectoryStream(root, "foo*.txt")) {
      int count = 0;
      for (Path path : paths) {
        assertThat(path).isEqualTo(f0);
        count++;
      }
      assertThat(count).isEqualTo(1);
    }

    try (DirectoryStream<Path> paths = Files.newDirectoryStream(root, "**/foo*.txt")) {
      int count = 0;
      for (@SuppressWarnings("unused") Path path : paths) {
        count++;
      }
      assertThat(count).isZero();
    }
  }

  @Test
  public void walkFileTree() throws Exception {
    PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/foo*.txt");
    List<Path> paths = new ArrayList<>();
    Files.walkFileTree(
        root,
        new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
            if (matcher.matches(path)) {
              paths.add(path);
            }
            return FileVisitResult.CONTINUE;
          }
        });
    assertThat(paths).containsExactlyInAnyOrder(f0, f1, f2).doesNotContain(b0);
  }

  @Test
  public void pathMatcher() {
    PathMatcher absMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.txt");
    assertThat(absMatcher.matches(f0)).isTrue();
    assertThat(absMatcher.matches(f1)).isTrue();
    assertThat(absMatcher.matches(f2)).isTrue();

    PathMatcher filenameMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
    assertThat(filenameMatcher.matches(f0.getFileName())).isTrue();
    assertThat(filenameMatcher.matches(f1.getFileName())).isTrue();
    assertThat(filenameMatcher.matches(f2.getFileName())).isTrue();
  }

  /**
   * The {@code *} character matches zero or more characters of a name component without crossing
   * directory boundaries.
   */
  @Test
  public void pathMatcher_wildcardWithoutCrossingBoundaries() {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:*.txt");
    assertThat(m.matches(Paths.get("/foo/bar.txt"))).isFalse();
    assertThat(m.matches(Paths.get("/foo/bar.md"))).isFalse();
    assertThat(m.matches(Paths.get("bar.txt"))).isTrue();
    assertThat(m.matches(Paths.get("bar.md"))).isFalse();
  }

  /** The {@code **} characters matches zero or more characters crossing directory boundaries. */
  @Test
  public void pathMatcher_wildcardWithCrossingBoundaries() {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:**.txt");
    assertThat(m.matches(Paths.get("/foo/bar.txt"))).isTrue();
    assertThat(m.matches(Paths.get("bar.txt"))).isTrue();
  }

  /** The {@code ?} character matches exactly one character of a name component. */
  @Test
  public void pathMatcher_exactlyOneChar() {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:?.txt");
    assertThat(m.matches(Paths.get("a.txt"))).isTrue();
    assertThat(m.matches(Paths.get("b.txt"))).isTrue();

    assertThat(m.matches(Paths.get(".txt"))).isFalse();
    assertThat(m.matches(Paths.get("ab.txt"))).isFalse();
    assertThat(m.matches(Paths.get("/foo/a.txt"))).isFalse();
    assertThat(m.matches(Paths.get("/foo/b.txt"))).isFalse();
  }

  @Test
  public void pathMatcher_bracketExpression() {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:[abc].txt");
    assertThat(m.matches(Paths.get("a.txt"))).isTrue();
    assertThat(m.matches(Paths.get("b.txt"))).isTrue();
    assertThat(m.matches(Paths.get("c.txt"))).isTrue();

    assertThat(m.matches(Paths.get("/foo/a.txt"))).isFalse();
    assertThat(m.matches(Paths.get("/foo/b.txt"))).isFalse();
    assertThat(m.matches(Paths.get("/foo/c.txt"))).isFalse();
    assertThat(m.matches(Paths.get("ab.txt"))).isFalse();
    assertThat(m.matches(Paths.get("d.txt"))).isFalse();
  }

  @Test
  public void pathMatcher_bracketExpressionNegation() {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:/foo/[!abc]*.txt");
    assertThat(m.matches(Paths.get("/foo/d.txt"))).isTrue();
    assertThat(m.matches(Paths.get("/foo/e.txt"))).isTrue();
    assertThat(m.matches(Paths.get("/foo/efg.txt"))).isTrue();

    assertThat(m.matches(Paths.get("a.txt"))).isFalse();
    assertThat(m.matches(Paths.get("b.txt"))).isFalse();
    assertThat(m.matches(Paths.get("c.txt"))).isFalse();
    assertThat(m.matches(Paths.get("d.txt"))).isFalse();
    assertThat(m.matches(Paths.get("/foo/a.txt"))).isFalse();
    assertThat(m.matches(Paths.get("/foo/b.txt"))).isFalse();
    assertThat(m.matches(Paths.get("/foo/c.txt"))).isFalse();
  }

  @Test
  public void pathMatcher_subPatterns() {
    PathMatcher m1 = FileSystems.getDefault().getPathMatcher("glob:*.{txt,md}");
    assertThat(m1.matches(Paths.get("a.txt"))).isTrue();
    assertThat(m1.matches(Paths.get("a.md"))).isTrue();
    assertThat(m1.matches(Paths.get("a.html"))).isFalse();

    PathMatcher m2 = FileSystems.getDefault().getPathMatcher("glob:*.{java,[abc]}");
    assertThat(m2.matches(Paths.get("foo.java"))).isTrue();
    assertThat(m2.matches(Paths.get("foo.a"))).isTrue();
    assertThat(m2.matches(Paths.get("foo.b"))).isTrue();
    assertThat(m2.matches(Paths.get("foo.c"))).isTrue();
    assertThat(m2.matches(Paths.get("foo.d"))).isFalse();
  }

  @Test
  public void pathMatcher_hiddenFiles() {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:*.gitignore");
    assertThat(m.matches(Paths.get(".gitignore"))).isTrue();
  }
}
