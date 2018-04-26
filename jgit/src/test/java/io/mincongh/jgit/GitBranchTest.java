package io.mincongh.jgit;

import java.util.List;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GitBranchTest extends JGitTest {

  @Test
  public void createBranch() throws Exception {
    Ref ref = git.branchCreate().setName("topic").call();
    assertThat(ref.getName()).isEqualTo("refs/heads/topic");
  }

  @Test(expected = RefNotFoundException.class)
  public void so50041078() throws Exception {
    git.branchCreate().setStartPoint("origin/topic").setName("topic").call();
  }

  @Test
  public void listBranches() throws Exception {
    git.branchCreate().setName("iss1").call();
    git.branchCreate().setName("iss2").call();

    List<Ref> refs = git.branchList().call();
    assertThat(refs).flatExtracting(this::shorten).containsOnly(repo.getBranch(), "iss1", "iss2");
  }

  private String shorten(Ref ref) {
    return Repository.shortenRefName(ref.getName());
  }
}
