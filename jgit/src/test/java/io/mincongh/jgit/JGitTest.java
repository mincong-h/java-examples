package io.mincongh.jgit;

import java.nio.file.Path;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * Base class for JGit unit tests.
 *
 * @author Mincong Huang
 */
public abstract class JGitTest {

  @Rule public final TemporaryFolder tempFolder = new TemporaryFolder();

  Git git;

  Repository repo;

  RevCommit initialCommit;

  Path root;

  @Before
  public void setUp() throws Exception {
    git = Git.init().setDirectory(tempFolder.getRoot()).call();
    root = tempFolder.getRoot().toPath();
    repo = git.getRepository();
    initialCommit = commit("Initial commit");
  }

  @After
  public void tearDown() {
    repo.close();
  }

  RevCommit commit(String message) throws GitAPIException {
    git.add().addFilepattern(".").call();
    return git.commit().setMessage(message).setAllowEmpty(true).call();
  }
}
