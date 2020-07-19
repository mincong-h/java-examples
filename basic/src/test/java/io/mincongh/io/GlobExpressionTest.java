package io.mincongh.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

  /**
   * The {@code **} characters matches zero or more characters crossing directory boundaries. Sign
   * "/" requires a directory boundary to exist in the path. Then, the {@code *} character matches
   * zero or more characters of a name component without crossing directory boundaries.
   */
  @ParameterizedTest
  @CsvSource({
    "/bar.txt,     true ",
    "/bar.md,      false", // unmatched: suffix
    "/foo/bar.txt, true ", //   matched: ** crosses dir boundary
    "/foo/bar.md,  false", // unmatched: suffix
    "bar.txt,      false", // unmatched: relative path (no dir)
    "bar.md,       false", // unmatched: relative path (no dir), suffix
  })
  void pathMatcher_undocumentedWildcardExpression(String path, boolean isMatched) {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:**/*.txt");
    assertThat(m.matches(Paths.get(path))).isEqualTo(isMatched);
  }

  /**
   * The {@code *} character matches zero or more characters of a name component without crossing
   * directory boundaries.
   */
  @ParameterizedTest
  @CsvSource({
    "/bar.txt,     false", // unmatched: dir, suffix
    "/bar.md,      false", // unmatched: dir, suffix
    "/foo/bar.txt, false", // unmatched: dir
    "/foo/bar.md,  false", // unmatched: dir, suffix
    "bar.txt,      true ",
    "bar.md,       false", // unmatched: suffix
  })
  void pathMatcher_wildcardWithoutCrossingBoundaries(String path, boolean isMatched) {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:*.txt");
    assertThat(m.matches(Paths.get(path))).isEqualTo(isMatched);
  }

  /** The {@code **} characters matches zero or more characters crossing directory boundaries. */
  @ParameterizedTest
  @CsvSource({
    "/bar.txt,     true ",
    "/bar.md,      false", // unmatched: suffix
    "/foo/bar.txt, true ", // matched: ** crosses dir boundary
    "/foo/bar.md,  false", // unmatched: suffix
    "bar.txt,      true ",
    "bar.md,       false", // unmatched: suffix
  })
  void pathMatcher_wildcardWithCrossingBoundaries(String path, boolean isMatched) {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:**.txt");
    assertThat(m.matches(Paths.get(path))).isEqualTo(isMatched);
  }

  /** The {@code ?} character matches exactly one character of a name component. */
  @ParameterizedTest
  @CsvSource({
    "a.txt,      true ",
    "b.txt,      true ",
    ".txt,       false",
    "ab.txt,     false",
    "/a.txt,     false",
    "/b.txt,     false",
    "/foo/a.txt, false",
    "/foo/b.txt, false",
  })
  void pathMatcher_exactlyOneChar(String path, boolean isMatched) {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:?.txt");
    assertThat(m.matches(Paths.get(path))).isEqualTo(isMatched);
  }

  @ParameterizedTest
  @CsvSource({
    "a.txt,      true ",
    "b.txt,      true ",
    "c.txt,      true ",
    "d.txt,      false",
    "ab.txt,     false",
    "/a.txt,     false",
    "/b.txt,     false",
    "/c.txt,     false",
    "/foo/a.txt, false",
    "/foo/b.txt, false",
    "/foo/c.txt, false",
  })
  void pathMatcher_bracketExpression(String path, boolean isMatched) {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:[abc].txt");
    assertThat(m.matches(Paths.get(path))).isEqualTo(isMatched);
  }

  @ParameterizedTest
  @CsvSource({
    "a.txt,        false",
    "b.txt,        false",
    "c.txt,        false",
    "d.txt,        false",
    "/a.txt,       false",
    "/b.txt,       false",
    "/c.txt,       false",
    "/d.txt,       false",
    "/def.txt,     false",
    "/foo/a.txt,   false",
    "/foo/b.txt,   false",
    "/foo/c.txt,   false",
    "/foo/d.txt,   true ",
    "/foo/def.txt, true ",
  })
  void pathMatcher_bracketExpressionNegation(String path, boolean isMatched) {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:/foo/[!abc]*.txt");
    assertThat(m.matches(Paths.get(path))).isEqualTo(isMatched);
  }

  @ParameterizedTest
  @CsvSource({
    "a.txt,  true ",
    "a.md,   true ",
    "a.html, false",
  })
  void pathMatcher_subPatterns1(String path, boolean isMatched) {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:*.{txt,md}");
    assertThat(m.matches(Paths.get(path))).isEqualTo(isMatched);
  }

  @ParameterizedTest
  @CsvSource({
    "foo.java, true ",
    "foo.a,    true ",
    "foo.b,    true ",
    "foo.c,    true ",
    "foo.d,    false",
  })
  void pathMatcher_subPatterns2(String path, boolean isMatched) {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:*.{java,[abc]}");
    assertThat(m.matches(Paths.get(path))).isEqualTo(isMatched);
  }

  @Test
  void pathMatcher_hiddenFiles() {
    PathMatcher m = FileSystems.getDefault().getPathMatcher("glob:*.gitignore");
    assertThat(m.matches(Paths.get(".gitignore"))).isTrue();
  }
}
