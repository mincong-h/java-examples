package io.mincongh.jgit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Tests methods related to Git references.
 *
 * @author Mincong Huang
 */
public class GitRefTest extends JGitTest {

  private List<Ref> refs;

  @Rule public final TemporaryFolder cloneFolder = new TemporaryFolder();

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();

    refs = new ArrayList<>();
    refs.add(git.branchCreate().setName("A").call());
    refs.add(git.branchCreate().setName("B").call());
    refs.add(git.branchCreate().setName("C").call());
  }

  @Test
  public void getHeads() throws Exception {
    Map<String, Ref> map = repo.getRefDatabase().getRefs(Constants.R_HEADS);
    assertThat(map).containsOnlyKeys("master", "A", "B", "C");
    assertThat(map.values()).containsAll(refs);
  }

  @Test
  public void getHEAD() throws Exception {
    Ref head = repo.getRefDatabase().getRef(Constants.HEAD);
    Ref master = repo.findRef("master");
    assertThat(head.getTarget()).isNotSameAs(head);
    assertThat(head.getTarget()).isSameAs(master);
  }

  @Test
  public void getBranchesOfCommit() throws Exception {
    CloneCommand clone =
        Git.cloneRepository()
            .setRemote("origin")
            .setURI(super.tempFolder.getRoot().toString())
            .setDirectory(cloneFolder.getRoot());

    try (Git git = clone.call()) {
      List<String> branches =
          git.branchList()
              .setContains(initialCommit.getId().name())
              .setListMode(ListMode.ALL)
              .call()
              .stream()
              .map(Ref::getName)
              .collect(Collectors.toList());
      assertThat(branches)
          .containsExactly(
              "refs/heads/master",
              "refs/remotes/origin/A",
              "refs/remotes/origin/B",
              "refs/remotes/origin/C",
              "refs/remotes/origin/master");
    }
  }
}
