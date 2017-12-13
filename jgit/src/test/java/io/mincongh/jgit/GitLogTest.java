package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class GitLogTest extends JGitTest {

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

}
