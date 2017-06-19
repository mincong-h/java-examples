import static org.assertj.core.api.Assertions.assertThat;

import io.mincongh.security.input.PathResolver;
import java.io.File;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Mincong Huang
 */
public class PathResolverTest {

  @Rule
  public TemporaryFolder defaultBaseDir = new TemporaryFolder();

  private Path defaultBaseDirPath;

  private Path defaultUserPath;

  @Before
  public void setUp() throws Exception {
    defaultUserPath = defaultBaseDir.newFile("user.txt").toPath();
  }

  @Test
  public void invalidBaseDirPath_relativePath() throws Exception {
    Path baseDirPath = new File("foo").toPath();
    assertThat(baseDirPath.isAbsolute()).isFalse();

    try {
      PathResolver.resolvePath(baseDirPath, defaultUserPath);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Base path must be absolute.");
    }
  }

}
