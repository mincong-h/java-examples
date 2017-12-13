package io.mincongh.jgit;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong huang
 */
public class GitRefTest extends JGitTest {

  private List<Ref> refs;

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

}
