import static org.assertj.core.api.Assertions.assertThat;

import io.mincongh.security.input.PathResolver;
import java.io.File;
import java.nio.file.Path;
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

    // The attack path is $TEMP_DIR/default/../attack.txt before normalization
    // The attach path is $TEMP_DIR/default/attack.txt after normalization
    Path resolvedAttackPath = defaultBaseDirPath.resolve(attackPath).normalize();
    assertThat(attackPath).isRelative();
    assertThat(resolvedAttackPath.startsWith(tempDir.getRoot().toPath())).isTrue();
    assertThat(resolvedAttackPath.startsWith(defaultBaseDirPath)).isFalse();

    expectedThrown.expect(IllegalArgumentException.class);
    expectedThrown.expectMessage("User path escapes the base path.");
    PathResolver.resolvePath(defaultBaseDirPath, attackPath);
  }

}
