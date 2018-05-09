package io.mincongh.jgit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Collections;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.hooks.CommitMsgHook;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * "git-commit" - Record changes to the repository
 *
 * @author Mincong Huang
 */
public class GitCommitTest extends JGitTest {

  @Before
  @Override
  public void setUp() throws Exception {
    super.setUp();
    Path readMe = new File(repo.getWorkTree(), "README.md").toPath();
    Files.write(readMe, Collections.singletonList("Hello world!"), StandardOpenOption.CREATE);
    git.add().addFilepattern(".").call();
  }

  @Test
  public void message() throws Exception {
    String message = "Add README.md\n\nI am message body";
    RevCommit c = git.commit().setMessage(message).call();
    assertThat(c.getShortMessage()).isEqualTo("Add README.md");
    assertThat(c.getFullMessage()).isEqualTo(message);
  }

  @Test
  public void author() throws Exception {
    PersonIdent author = new PersonIdent("Foo", "foo@example.com");
    RevCommit c1 = git.commit().setMessage("x").setAuthor(author).call();
    assertThat(c1.getAuthorIdent().getEmailAddress()).isEqualTo("foo@example.com");
    assertThat(c1.getAuthorIdent().getName()).isEqualTo("Foo");

    RevCommit c2 = git.commit().setMessage("x").setAuthor("Foo", "foo@example.com").call();
    assertThat(c2.getAuthorIdent().getEmailAddress()).isEqualTo("foo@example.com");
    assertThat(c2.getAuthorIdent().getName()).isEqualTo("Foo");
  }

  @Test
  public void committer() throws Exception {
    PersonIdent committer = new PersonIdent("Foo", "foo@example.com");
    RevCommit c1 = git.commit().setMessage("x").setCommitter(committer).call();
    assertThat(c1.getAuthorIdent().getEmailAddress()).isEqualTo("foo@example.com");
    assertThat(c1.getAuthorIdent().getName()).isEqualTo("Foo");

    RevCommit c2 = git.commit().setMessage("x").setCommitter(committer).call();
    assertThat(c2.getAuthorIdent().getEmailAddress()).isEqualTo("foo@example.com");
    assertThat(c2.getAuthorIdent().getName()).isEqualTo("Foo");
  }

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
  public void parents() throws Exception {
    // Given a two-branch repository
    Ref base = git.branchCreate().setName("topic").call();
    RevCommit m1 = commit("M1");
    git.checkout().setName("topic").call();
    RevCommit t1 = commit("T1");
    RevCommit t2 = commit("T2");

    // When merged successfully
    git.checkout().setName("master").call();
    MergeResult result = git.merge().include(t2).setMessage("M2").call();

    // Then merged commit has 2 parents, while other commits have only one.
    try (RevWalk revWalk = new RevWalk(repo)) {
      RevCommit m2 = revWalk.parseCommit(result.getNewHead());
      RevCommit m0 = revWalk.parseCommit(base.getObjectId());

      assertThat(m2.getParents()).containsExactly(m1, t2);
      assertThat(t2.getParents()).containsExactly(t1);
      assertThat(t1.getParents()).containsExactly(m0);
      assertThat(m1.getParents()).containsExactly(m0);
    }
  }

  @Test
  public void commitMsgHook() throws Exception {
    // Given an always-failed commit-msg hook
    writeHookFile(CommitMsgHook.NAME, "#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 1");

    // When adding content and commit
    String path = "a.txt";
    Files.write(root.resolve(path), Collections.singletonList("content"));
    git.add().addFilepattern(path).call();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      git.commit().setMessage("commit").setHookOutputStream(new PrintStream(out)).call();
      fail("expected commit-msg hook to abort commit");
    } catch (AbortedByHookException e) {
      // Then the commit hook is triggered
      assertThat(e.getMessage())
          .as("unexpected error message from commit-msg hook")
          .isEqualTo("Rejected by \"commit-msg\" hook.\nstderr\n");
      assertThat("test\n")
          .as("unexpected output from commit-msg hook", "test\n")
          .isEqualTo(out.toString());
    }
  }

  private void writeHookFile(String name, String data) throws IOException {
    Path path = repo.getWorkTree().toPath().resolve(".git/hooks").resolve(name);
    Files.write(path, Collections.singletonList(data), StandardOpenOption.CREATE);
    Files.setPosixFilePermissions(path, PosixFilePermissions.fromString("rwxr-xr-x"));
  }
}
