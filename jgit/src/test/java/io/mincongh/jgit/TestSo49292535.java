package io.mincongh.jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * How to do git push using JGit API?
 *
 * @author Mincong Huang
 * @see <a href="https://stackoverflow.com/questions/49292535">How to do git push using JGit
 *     API?</a>
 */
public class TestSo49292535 {

  @Rule public final TemporaryFolder tempDir = new TemporaryFolder();

  /**
   * Using {@link FileRepository} will create a Git bare repo. Commit directly to BARE repository
   * will fail: because repository does not have work tree, thus it is not in the right state for
   * committing.
   *
   * <p>NOTE: This is an internal API.
   */
  @Test(expected = WrongRepositoryStateException.class)
  public void useFileRepository() throws Exception {
    try (FileRepository repository = new FileRepository(tempDir.getRoot())) {
      assertThat(repository.isBare()).isTrue();
      Git.wrap(repository).commit().setAllowEmpty(true).setMessage("Should fail").call();
      fail("Commit directory to bare repository should fail.");
    }
  }

  @Test
  public void useGit() throws Exception {
    // Using Git init with only `setDirectory` option won't create a bare repository.
    try (Git git = Git.init().setDirectory(tempDir.getRoot()).call()) {
      assertThat(git.getRepository().isBare()).isFalse();
    }

    // Using Git open won't create a bare repository neither.
    try (Git git = Git.open(tempDir.getRoot())) {
      assertThat(git.getRepository().isBare()).isFalse();
    }
  }
}
