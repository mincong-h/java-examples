package io.mincongh.jgit;

import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.Ref;
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
}
