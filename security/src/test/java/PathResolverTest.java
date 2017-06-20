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

  // TODO use another temporary folder to limit to attack directory & ensure the cleanup

  @Rule
  public final TemporaryFolder defaultBaseDir = new TemporaryFolder();

  @Rule
  public final ExpectedException expectedThrown = ExpectedException.none();

  private Path defaultBaseDirPath;

  private Path defaultUserPath;

  @Before
  public void setUp() throws Exception {
    defaultBaseDirPath = defaultBaseDir.getRoot().toPath();
    defaultUserPath = defaultBaseDir.newFile("user.txt").toPath();
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
    Path attackPath = null;
    try {
      attackPath = defaultBaseDir.newFile("../attack.txt").toPath();
      Path resolvedAttackPath = defaultBaseDirPath.resolve(attackPath).normalize();
      assertThat(resolvedAttackPath.startsWith(defaultBaseDirPath)).isFalse();

      expectedThrown.expect(IllegalArgumentException.class);
      expectedThrown.expectMessage("User path escapes the base path.");
      PathResolver.resolvePath(defaultBaseDirPath, attackPath);
    } finally {
      if (attackPath != null) {
        attackPath.toFile().delete();
      }
    }
  }

}
