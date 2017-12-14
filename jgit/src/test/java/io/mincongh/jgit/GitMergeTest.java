package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class GitMergeTest extends JGitTest {

  /**
   * Commit graph:
   * <pre>
   * M0 --- M1 --- M2 (HEAD -> master)
   *   \          /
   *    T1 --- T2 (topic)
   * </pre>
   */
  @Test
  public void merge() throws Exception {
    // Given a two-branch repository
    git.branchCreate().setName("topic").call();
    commit("M1");
    git.checkout().setName("topic").call();
    commit("T1");
    RevCommit t2 = commit("T2");

    // When merged
    git.checkout().setName("master").call();
    MergeResult result = git.merge().include(t2).setMessage("M2").call();

    // Then the merged commit is M2
    try (RevWalk revWalk = new RevWalk(repo)) {
      RevCommit commit = revWalk.parseCommit(result.getNewHead());
      assertThat(commit.getShortMessage()).isEqualTo("M2");
    }
  }

}
