package io.mincongh.jgit;

import java.nio.file.Files;
import java.util.Iterator;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class WalkTest extends JGitTest {

  @Test
  public void revWalk_UntilMergeBase() throws Exception {
    git.checkout().setCreateBranch(true).setName("topic").call();

    Files.write(root.resolve("readme"), singletonList("L1"));
    RevCommit t1 = commit("T1");

    Files.write(root.resolve("readme"), singletonList("L2"));
    RevCommit t2 = commit("T2");

    try (RevWalk revWalk = new RevWalk(repo)) {
      revWalk.markStart(t2);
      revWalk.markUninteresting(initialCommit);
      Iterator<RevCommit> it = revWalk.iterator();
      assertThat(it.next().name()).isEqualTo(t2.name());
      assertThat(it.next().name()).isEqualTo(t1.name());
      assertThat(revWalk.iterator().hasNext()).isFalse();
    }
  }

  @Test
  public void treeWalk() throws Exception {
    git.checkout().setCreateBranch(true).setName("topic").call();

    Files.write(root.resolve("readme"), singletonList("L1"));
    commit("T1");

    Files.write(root.resolve("readme"), singletonList("L2"));
    RevCommit t2 = commit("T2");

    git.log().all().call().forEach(System.out::println);

    try (RevWalk revWalk = new RevWalk(repo)) {
      ObjectWalk objWalk = revWalk.toObjectWalkWithSameObjects();
      objWalk.markStart(revWalk.parseCommit(t2.getId()));
      objWalk.markUninteresting(revWalk.parseCommit(initialCommit.getId()));

      RevObject rev = objWalk.next();
      while (rev != null) {
        System.out.println(rev);
        rev = objWalk.next();
      }

      int blobs = 0;
      rev = objWalk.nextObject();
      while (rev != null) {
        if (rev.getType() == Constants.OBJ_TREE) {
          RevTree tree = (RevTree) rev;
          try (TreeWalk treeWalk = new TreeWalk(repo)) {
            treeWalk.addTree(tree);
            treeWalk.setRecursive(false);
            while (treeWalk.next()) {
              assertThat(treeWalk.getPathString()).isEqualTo("readme");
              blobs++;
            }
          }
        }
        rev = objWalk.nextObject();
      }
      assertThat(blobs).isEqualTo(2);
    }
  }
}
