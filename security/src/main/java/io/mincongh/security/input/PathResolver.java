package io.mincongh.security.input;

import java.nio.file.Path;

/**
 * Provides valid path resolution.
 * <p>
 * Copied from <a href="https://stackoverflow.com/questions/33083397">Filtering
 * upwards path traversal in Java (or Scala) [closed]</a>.
 *
 * @author <a href="https://stackoverflow.com/users/226975/wyzard">Wyzard</a>
 */
public final class PathResolver {

  private PathResolver() {
    // Utility class, do not instantiate
  }

  /**
   * Resolves an untrusted user-specified path against the API's base
   * directory. Paths that try to escape the base directory are
   * rejected.
   *
   * @param baseDirPath the absolute path of the base directory that all user-specified paths should
   * be within
   * @param userPath the untrusted path provided by the API user, expected to be relative to {@code
   * baseDirPath}
   */
  public static Path resolvePath(final Path baseDirPath, final Path userPath) {
    if (!baseDirPath.isAbsolute()) {
      throw new IllegalArgumentException("Base path must be absolute.");
    }
    if (userPath.isAbsolute()) {
      throw new IllegalArgumentException("User path must be relative.");
    }

    /*
     * Join the two paths together, then normalize so that any ".."
     * elements in the userPath can remove parts of baseDirPath, e.g.
     * "/foo/bar/" + "../attack" -> "/foo/attack"
     */
    final Path resolvedPath = baseDirPath.resolve(userPath).normalize();

    /*
     * Make sure the resulting path is still within the required
     * directory. For example, "/foo/attack" is not.
     */
    if (!resolvedPath.startsWith(baseDirPath)) {
      throw new IllegalArgumentException("User path escapes the base path.");
    }
    return resolvedPath;
  }

}
