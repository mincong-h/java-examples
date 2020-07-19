package io.mincongh.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test glob expression in file methods in Java 8.
 *
 * @author Mincong Huang
 * @since 1.0
 * @blog https://mincong.io/2019/04/16/glob-expression-understanding/
 */
class GlobExpressionTest {
  @TempDir Path root;

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
  @BeforeEach
  void setUp() throws Exception {
    f0 = Files.createFile(root.resolve("foo0.txt"));
    b0 = Files.createFile(root.resolve("bar0.txt"));
    d1 = Files.createDirectory(root.resolve("sub1"));
    f1 = Files.createFile(d1.resolve("foo1.txt"));
    d2 = Files.createDirectory(root.resolve("sub2"));
    f2 = Files.createFile(d2.resolve("foo2.txt"));
  }

  /**
   * Method {@link Files#newDirectoryStream(Path, String)} iterate the given directory path.
   * However, it will NOT iterate recursively the files in sub-directories.
   */
  @Test
  void newDirectoryStream() throws Exception {
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
  void walkFileTree() throws Exception {
    PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/foo*.txt");
    List<Path> paths = new ArrayList<>();
    Files.walkFileTree(
        root,
        new SimpleFileVisitor<>() {
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
  void pathMatcher_undocumentedWildcardExpression() {
    // `**/*.txt` is not mentionned in Javadoc:
    // https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileSystem.html#getPathMatcher-java.lang.String-
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:**/*.txt");

    assertThat(m.matches(Paths.get("/bar.txt"))).isTrue();
    assertThat(m.matches(Paths.get("/bar.md"))).isFalse(); // unmatched: suffix
    assertThat(m.matches(Paths.get("/foo/bar.txt"))).isTrue(); // matched: ** crosses dir boundary
    assertThat(m.matches(Paths.get("/foo/bar.md"))).isFalse(); // unmatched: suffix
    assertThat(m.matches(Paths.get("bar.txt"))).isFalse(); // unmatched: relative path (no dir)
    assertThat(m.matches(Paths.get("bar.md")))
        .isFalse(); // unmatched: relative path (no dir), suffix

    assertThat(m.matches(f0)).isTrue();
    assertThat(m.matches(f1)).isTrue();
    assertThat(m.matches(f2)).isTrue();
  }

  /**
   * The {@code *} character matches zero or more characters of a name component without crossing
   * directory boundaries.
   */
  @Test
  void pathMatcher_wildcardWithoutCrossingBoundaries() {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:*.txt");

    assertThat(m.matches(Paths.get("/bar.txt"))).isFalse(); // unmatched: dir, suffix
    assertThat(m.matches(Paths.get("/bar.md"))).isFalse(); // unmatched: dir, suffix
    assertThat(m.matches(Paths.get("/foo/bar.txt"))).isFalse(); // unmatched: dir
    assertThat(m.matches(Paths.get("/foo/bar.md"))).isFalse(); // unmatched: dir, suffix
    assertThat(m.matches(Paths.get("bar.txt"))).isTrue();
    assertThat(m.matches(Paths.get("bar.md"))).isFalse(); // unmatched: suffix

    assertThat(m.matches(f0.getFileName())).isTrue();
    assertThat(m.matches(f1.getFileName())).isTrue();
    assertThat(m.matches(f2.getFileName())).isTrue();
  }

  /** The {@code **} characters matches zero or more characters crossing directory boundaries. */
  @Test
  void pathMatcher_wildcardWithCrossingBoundaries() {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:**.txt");

    assertThat(m.matches(Paths.get("/bar.txt"))).isTrue();
    assertThat(m.matches(Paths.get("/bar.md"))).isFalse(); // unmatched: suffix
    assertThat(m.matches(Paths.get("/foo/bar.txt"))).isTrue(); // matched: ** crosses dir boundary
    assertThat(m.matches(Paths.get("/foo/bar.md"))).isFalse(); // unmatched: suffix
    assertThat(m.matches(Paths.get("bar.txt"))).isTrue();
    assertThat(m.matches(Paths.get("bar.md"))).isFalse(); // unmatched: suffix
  }

  /** The {@code ?} character matches exactly one character of a name component. */
  @Test
  void pathMatcher_exactlyOneChar() {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:?.txt");
    assertThat(m.matches(Paths.get("a.txt"))).isTrue();
    assertThat(m.matches(Paths.get("b.txt"))).isTrue();

    assertThat(m.matches(Paths.get(".txt"))).isFalse();
    assertThat(m.matches(Paths.get("ab.txt"))).isFalse();
    assertThat(m.matches(Paths.get("/foo/a.txt"))).isFalse();
    assertThat(m.matches(Paths.get("/foo/b.txt"))).isFalse();
  }

  @Test
  void pathMatcher_bracketExpression() {
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
  void pathMatcher_bracketExpressionNegation() {
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
  void pathMatcher_subPatterns() {
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
  void pathMatcher_hiddenFiles() {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:*.gitignore");
    assertThat(m.matches(Paths.get(".gitignore"))).isTrue();
  }
}
