package io.mincongh.jgit;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests different Git object references expression in JGit.
 *
 * @author Mincong Huang
 */
public class GitRevObjectTest extends JGitTest {

  /** Resolving HEAD points to commit. */
  @Test
  public void resolveHead() throws Exception {
    ObjectId commitId = repo.resolve(Constants.HEAD);
    try (RevWalk walk = new RevWalk(repo)) {
      RevObject object = walk.parseAny(commitId);
      assertThat(object.getType()).isEqualTo(Constants.OBJ_COMMIT);
      assertThat(object).isEqualTo(initialCommit);
    }
  }

  /** Resolving commit hash (SHA-1) points to commit itself. */
  @Test
  public void resolveCommitHash() throws Exception {
    String revStr = initialCommit.name();
    ObjectId commitId = repo.resolve(revStr);
    try (RevWalk walk = new RevWalk(repo)) {
      RevObject object = walk.parseAny(commitId);
      assertThat(object.getType()).isEqualTo(Constants.OBJ_COMMIT);
      assertThat(object).isEqualTo(initialCommit);
    }
  }

  /** Resolving <tt>hash^{tree}</tt> points to commit tree. */
  @Test
  public void resolveTree() throws Exception {
    String revStr = initialCommit.name() + "^{tree}";
    ObjectId treeId = repo.resolve(revStr);
    try (RevWalk walk = new RevWalk(repo)) {
      // Unknown type (any)
      RevObject object = walk.parseAny(treeId);
      assertThat(object.getType()).isEqualTo(Constants.OBJ_TREE);
      assertThat(object).isEqualTo(initialCommit.getTree());

      // Known type (tree)
      RevTree tree = walk.parseTree(treeId);
      assertThat(tree).isEqualTo(initialCommit.getTree());
    }
  }

  /** Resolving branch name points to commit. */
  @Test
  public void resolveBranch() throws Exception {
    ObjectId commitId = repo.resolve(Constants.MASTER);
    try (RevWalk walk = new RevWalk(repo)) {
      // Unknown type (any)
      RevObject object = walk.parseAny(commitId);
      assertThat(object.getType()).isEqualTo(Constants.OBJ_COMMIT);
      assertThat(object).isEqualTo(initialCommit);

      // Known type (commit)
      RevCommit commit = walk.parseCommit(commitId);
      assertThat(commit).isEqualTo(initialCommit);
    }
  }

  /** Resolving annotated tag points to tag itself. */
  @Test
  public void resolveTag_annotatedTag() throws Exception {
    Ref tagRef = git.tag().setAnnotated(true).setName("1.0").call();
    ObjectId tagId = repo.resolve("1.0");
    try (RevWalk walk = new RevWalk(repo)) {
      // Unknown type (any)
      RevObject object = walk.parseAny(tagId);
      assertThat(object.getType()).isEqualTo(Constants.OBJ_TAG);
      assertThat(object.getId()).isEqualTo(tagRef.getObjectId());

      // Known type (commit)
      RevTag tag = walk.parseTag(tagId);
      assertThat(tag.getTagName()).isEqualTo("1.0");
    }
  }

  /** Resolving recursively (peel) annotated tag points to tag itself. */
  @Test
  public void resolveTag_annotatedTagPeeled() throws Exception {
    git.tag().setAnnotated(true).setName("1.0").call();
    ObjectId tagId = repo.resolve("1.0");
    try (RevWalk walk = new RevWalk(repo)) {
      RevObject object = walk.parseCommit(tagId);
      assertThat(object.getType()).isEqualTo(Constants.OBJ_COMMIT);
      assertThat(object).isEqualTo(initialCommit);
    }
  }

  /** Resolving lightweight tag points to commit. */
  @Test
  public void resolveTag_lightweightTag() throws Exception {
    git.tag().setAnnotated(false).setName("1.0-lw").call();
    ObjectId tagId = repo.resolve("1.0-lw");
    try (RevWalk walk = new RevWalk(repo)) {
      // Unknown type (any)
      RevObject object = walk.parseAny(tagId);
      assertThat(object.getType()).isEqualTo(Constants.OBJ_COMMIT);
      assertThat(object).isEqualTo(initialCommit);

      // Known type (commit)
      RevCommit commit = walk.parseCommit(tagId);
      assertThat(commit).isEqualTo(initialCommit);
    }
  }
}
