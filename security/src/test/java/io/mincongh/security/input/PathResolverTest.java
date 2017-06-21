package io.mincongh.security.input;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

/**
 * @author Mincong Huang
 */
public class PathResolverTest {

  @Rule
  public final TemporaryFolder tempDir = new TemporaryFolder();

  @Rule
  public final ExpectedException expectedThrown = ExpectedException.none();

  /**
   * The absolute path of the default base directory of each test.
   * The directory, matched with this path, is located at the root
   * level of {@link #tempDir}:
   *
   * <pre><code>
   * $TEMP_DIR/default
   * </code></pre>
   */
  private Path defaultBaseDirPath;

  /**
   * The relative path of the default user file path of each test.
   * The file, matched with this path, is located at the root
   * directory of {@link #defaultBaseDirPath}:
   *
   * <pre><code>
   * $TEMP_DIR/default/user.txt
   * </code></pre>
   */
  private Path defaultUserPath;

  @Before
  public void setUp() throws Exception {
    File baseDir = tempDir.newFolder("default");
    File userFile = new File(baseDir, "user.txt");
    defaultBaseDirPath = baseDir.toPath().toAbsolutePath();
    defaultUserPath = defaultBaseDirPath.relativize(userFile.toPath().toAbsolutePath());

    assertThat(defaultBaseDirPath).isAbsolute();
    assertThat(defaultBaseDirPath).startsWith(tempDir.getRoot().toPath());
    assertThat(defaultUserPath).isRelative();
    Path resolvedPath = defaultBaseDirPath.resolve(defaultUserPath);
    assertThat(resolvedPath.startsWith(defaultBaseDirPath)).isTrue();
  }

  @Test
  public void validPathResolution() throws Exception {
    Path resolvedPath = PathResolver.resolvePath(defaultBaseDirPath, defaultUserPath);

    assertThat(resolvedPath).isAbsolute();
    assertThat(resolvedPath).isNormalized();
    assertThat(resolvedPath.startsWith(defaultBaseDirPath)).isTrue();
  }

  @Test
  public void invalidBaseDirPath_relativePath() throws Exception {
    Path baseDirPath = new File("foo").toPath();
    assertThat(baseDirPath.isAbsolute()).isFalse();

    expectedThrown.expect(IllegalArgumentException.class);
    expectedThrown.expectMessage("Base path must be absolute.");
    PathResolver.resolvePath(baseDirPath, defaultUserPath);
  }

  @Test(expected = NullPointerException.class)
  public void invalidBaseDirPath_nullPath() throws Exception {
    PathResolver.resolvePath(null, defaultUserPath);
  }

  @Test
  public void invalidUserPath_absolutePath() throws Exception {
    Path absoluteUserPath = defaultUserPath.toAbsolutePath();

    expectedThrown.expect(IllegalArgumentException.class);
    expectedThrown.expectMessage("User path must be relative.");
    PathResolver.resolvePath(defaultBaseDirPath, absoluteUserPath);
  }

  @Test(expected = NullPointerException.class)
  public void invalidUserPath_nullPath() throws Exception {
    PathResolver.resolvePath(defaultBaseDirPath, null);
  }

  @Test
  public void invalidUserPath_escapedPath() throws Exception {
    File attackFile = new File(defaultBaseDirPath + "../attack.txt");
    Path attackPath = defaultBaseDirPath.relativize(attackFile.toPath());

    /*
     * The attack path escapes from the base dir:
     * <li>Before normalization, it is $TEMP_DIR/default/../attack.txt
     * <li>After normalization, it is $TEMP_DIR/attack.txt
     */
    Path resolvedAttackPath = defaultBaseDirPath.resolve(attackPath).normalize();
    assertThat(attackPath).isRelative();
    assertThat(resolvedAttackPath.startsWith(tempDir.getRoot().toPath())).isTrue();
    assertThat(resolvedAttackPath.startsWith(defaultBaseDirPath)).isFalse();

    expectedThrown.expect(IllegalArgumentException.class);
    expectedThrown.expectMessage("User path escapes the base path.");
    PathResolver.resolvePath(defaultBaseDirPath, attackPath);
  }

  @Test
  public void validStrResolution() throws Exception {
    String baseStr = defaultBaseDirPath.toString();
    Path resolvedPath = PathResolver.resolvePath(baseStr, "user.txt");

    assertThat(resolvedPath.startsWith(defaultBaseDirPath)).isTrue();
  }

  @Test
  public void invalidBaseDirStr_relativePath() throws Exception {
    expectedThrown.expect(IllegalArgumentException.class);
    expectedThrown.expectMessage("Base path must be absolute.");
    PathResolver.resolvePath("basedir", "user.txt");
  }

  @Test(expected = NullPointerException.class)
  public void invalidBaseDirStr_nullPath() throws Exception {
    PathResolver.resolvePath(null, "user.txt");
  }

  @Test(expected = NullPointerException.class)
  public void invalidUserStr_nullPath() throws Exception {
    String baseStr = defaultBaseDirPath.toString();
    PathResolver.resolvePath(baseStr, null);
  }

  @Test
  public void invalidUserStr_absolutePath() throws Exception {
    String baseStr = defaultBaseDirPath.toString();

    expectedThrown.expect(IllegalArgumentException.class);
    expectedThrown.expectMessage("User path must be relative.");
    PathResolver.resolvePath(baseStr, "/user.txt");
  }

  @Test
  public void invalidUserStr_escapedPath() throws Exception {
    List<String> values = new ArrayList<>();
    values.add("../user.txt");
    values.add("child/../../user.txt");
    values.add("%2e%2e%2fuser.txt");
    values.add("%2e%2e/user.txt");
    values.add("..%2fuser.txt");

    values.forEach(this::assertEscaped);
  }

  private void assertEscaped(String userRelativePath) {
    String baseStr = defaultBaseDirPath.toString();

    expectedThrown.expect(IllegalArgumentException.class);
    expectedThrown.expectMessage("User path escapes the base path.");
    PathResolver.resolvePath(baseStr, userRelativePath);
  }

}
