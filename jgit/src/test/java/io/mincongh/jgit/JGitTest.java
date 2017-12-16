package io.mincongh.jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Mincong Huang
 */
public abstract class JGitTest {

  @Rule
  public final TemporaryFolder tempFolder = new TemporaryFolder();

  Git git;

  Repository repo;

  @Before
  public void setUp() throws Exception {
    git = Git.init().setDirectory(tempFolder.getRoot()).call();
    repo = git.getRepository();
    commit("Initial commit");
  }

  @After
  public void tearDown() throws Exception {
    repo.close();
  }

  RevCommit commit(String message) throws GitAPIException {
    git.add().addFilepattern(".").call();
    return git.commit().setMessage(message).setAllowEmpty(true).call();
  }

}
