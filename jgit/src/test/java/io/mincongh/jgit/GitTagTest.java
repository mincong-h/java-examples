package io.mincongh.jgit;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests git-tag commands.
 *
 * @author Mincong Huang
 */
public class GitTagTest extends JGitTest {

  @Test
  public void createTag_lightweight() throws Exception {
    Ref ref = git.tag().setAnnotated(false).setName("1.0").call();
    assertThat(ref.getName()).isEqualTo("refs/tags/1.0");

    try (RevWalk walk = new RevWalk(repo)) {
      RevObject obj = walk.parseAny(ref.getObjectId());

      // A lightweight tag points to a commit directly. It does not
      // contains information about author, message, or date. In
      // native command, it is:
      //
      //     git cat-file -t <tag>
      //
      assertThat(obj.getType()).isEqualTo(Constants.OBJ_COMMIT);
      assertThat(obj).isEqualTo(initialCommit);
    }
  }

  @Test
  public void createTag_annotated() throws Exception {
    PersonIdent tagger = new PersonIdent("Foo", "foo@example.com");
    Ref ref =
        git.tag()
            .setAnnotated(true)
            .setName("1.0")
            .setMessage("Release 1.0")
            .setTagger(tagger)
            .call();
    assertThat(ref.getName()).isEqualTo("refs/tags/1.0");

    try (RevWalk walk = new RevWalk(repo)) {
      RevTag tag = walk.parseTag(ref.getObjectId());

      // An annotated tag contains information about author, message,
      // and date. It does not point to commit directly. In native
      // command, it is:
      //
      //    git cat-file -t <tag>
      //
      assertThat(tag.getType()).isEqualTo(Constants.OBJ_TAG);
      assertThat(tag.getShortMessage()).isEqualTo("Release 1.0");
      assertThat(tag.getFullMessage()).isEqualTo("Release 1.0");
      assertThat(tag.getObject()).isEqualTo(initialCommit);
      assertThat(tag.getTaggerIdent()).isEqualTo(tagger);
      assertThat(tag.getTagName()).isEqualTo("1.0");
    }
  }

  @Test(expected = JGitInternalException.class)
  public void createTag_tagNoChange() throws Exception {
    git.tag().setName("1.0").call();
    git.tag().setName("1.0").call();
  }

  @Test(expected = RefAlreadyExistsException.class)
  public void createTag_tagAlreadyExists() throws Exception {
    git.tag().setName("1.0").call();
    super.commit("Fix bugs");
    git.tag().setName("1.0").call();
  }

  @Test
  public void listTags() throws Exception {
    Ref tagL = git.tag().setAnnotated(false).setName("lightweight").call();
    Ref tagA = git.tag().setAnnotated(true).setName("annotated").call();
    assertThat(git.tagList().call()).containsExactly(tagA, tagL);
    assertThat(git.tagList().call())
        .extracting(Ref::getName)
        .containsExactly("refs/tags/annotated", "refs/tags/lightweight");
  }

  @Test(expected = NullPointerException.class)
  public void getTag_nonexistent() throws Exception {
    ObjectId tagId = repo.resolve("nonexistent");
    try (RevWalk walk = new RevWalk(repo)) {
      walk.parseTag(tagId);
    }
  }

  @Test
  public void deleteTag() throws Exception {
    git.tag().setName("1.0").call();
    git.tagDelete().setTags("1.0").call();
    assertThat(git.tagList().call()).isEmpty();
  }
}
