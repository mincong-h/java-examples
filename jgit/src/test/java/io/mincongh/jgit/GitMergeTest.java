package io.mincongh.jgit;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** @author Mincong Huang */
public class GitMergeTest extends JGitTest {

  /**
   * Commit graph:
   *
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

  /**
   * Commit graph:
   *
   * <pre>
   * M0 --- M1 --- M2 --- M3 (HEAD -> master)
   *   |          /
   *    T1 --- T2 (topic)
   * </pre>
   *
   * @see <a href="https://stackoverflow.com/questions/49792793">Git log full history</a>
   */
  @Test
  public void showMergeCommit() throws Exception {
    // Given a two-branch repository, where file `foo` is changed on
    // `topic` branch, then changed in `master` branch.
    git.branchCreate().setName("topic").call();
    commit("M1");

    git.checkout().setName("topic").call();
    Path foo = root.resolve("foo");

    Files.createFile(foo);
    RevCommit t1 = commit("Create file foo");

    Files.write(foo, Arrays.asList("L1", "L2"));
    RevCommit t2 = commit("Add lines in foo");

    git.checkout().setName("master").call();
    MergeResult result = git.merge().include(t2).setMessage("M2").call();
    RevCommit m2 = repo.parseCommit(result.getNewHead());

    Files.write(foo, Arrays.asList("L3", "L4"), StandardOpenOption.APPEND);
    RevCommit m3 = commit("Add more lines in foo");

    // When showing the log history of file `foo`
    List<RevCommit> commits = new ArrayList<>();
    git.log().addPath("foo").call().forEach(commits::add);

    // Then 3 commits [t1, t2, m3] are shown, but not the merged commit m2.
    assertThat(commits).contains(t1, t2, m3).doesNotContain(m2);
  }
}
