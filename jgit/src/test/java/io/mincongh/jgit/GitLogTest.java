package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class GitLogTest {

  @Rule
  public final TemporaryFolder tempFolder = new TemporaryFolder();

  private Git git;

  private Repository repo;

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

  /**
   * Given a Git repository with 2 branches:
   * <pre>
   * * --> M1 --> M2 (master)
   *  \
   *   --> T1 --> T2 (topic)
   * </pre>
   * Using git-log commands in different scenario:
   * <ul>
   * <li><tt>git log master..topic</tt> returns ["T2", "T1"].
   * <li><tt>git log topic..master</tt> returns ["M2", "M1"].
   * </ul>
   */
  @Test
  public void gitLog_range() throws Exception {
    git.checkout().setCreateBranch(true).setName("topic").call();
    commit("T1");
    commit("T2");

    git.checkout().setName("master").call();
    commit("M1");
    commit("M2");

    ObjectId master = repo.resolve("master");
    ObjectId topic = repo.resolve("topic");

    assertThat(git.log().addRange(master, topic).call())
        .flatExtracting(RevCommit::getShortMessage)
        .containsExactly("T2", "T1");

    assertThat(git.log().addRange(topic, master).call())
        .flatExtracting(RevCommit::getShortMessage)
        .containsExactly("M2", "M1");
  }

  private void commit(String message) throws Exception {
    git.add().addFilepattern(".").call();
    git.commit().setMessage(message).setAllowEmpty(true).call();
  }

}
