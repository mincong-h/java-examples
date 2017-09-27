package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * "git-commit" - Record changes to the repository
 *
 * @author Mincong Huang
 */
public class GitCommitTest {

  @Rule
  public final TemporaryFolder tempFolder = new TemporaryFolder();

  private Git git;

  private Repository repo;

  @Before
  public void setUp() throws Exception {
    git = Git.init().setDirectory(tempFolder.getRoot()).call();
    repo = git.getRepository();

    Path readMe = new File(repo.getWorkTree(), "README.md").toPath();
    Files.write(readMe, Collections.singletonList("Hello world!"), StandardOpenOption.CREATE);
    git.add().addFilepattern(".").call();
  }

  @After
  public void tearDown() throws Exception {
    repo.close();
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

}
