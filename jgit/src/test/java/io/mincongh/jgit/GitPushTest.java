package io.mincongh.jgit;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests Git push and Git clone.
 *
 * @author Mincong Huang
 */
public class GitPushTest {

  @Rule public final TemporaryFolder remoteFolder = new TemporaryFolder();

  @Rule public final TemporaryFolder clonedFolder = new TemporaryFolder();

  private Path remoteDir;

  private Path clonedDir;

  @Before
  public void setUp() throws Exception {
    remoteDir = remoteFolder.getRoot().toPath();
    clonedDir = clonedFolder.getRoot().toPath();

    Git.init().setDirectory(remoteDir.toFile()).setBare(true).call();
  }

  @Test
  public void cloneAndPush() throws Exception {
    // Given a cloned repository
    CloneCommand cloneCmd =
        Git.cloneRepository()
            .setDirectory(clonedFolder.getRoot())
            .setURI(remoteDir.toUri().toString());

    // When commit and push to remote
    try (Git git = cloneCmd.call()) {
      Files.write(clonedDir.resolve("readMe"), Arrays.asList("3", "4"), StandardOpenOption.CREATE);
      git.add().addFilepattern(".").call();
      git.commit().setMessage("2nd commit").call();
      git.push().setRemote("origin").add("master").call();
    }

    // Then remote should receive the commit
    try (Git git = Git.open(remoteDir.toFile())) {
      List<Ref> branches = git.branchList().call();
      assertThat(branches).flatExtracting(Ref::getName).containsExactly("refs/heads/master");
      try (RevWalk walk = new RevWalk(git.getRepository())) {
        RevCommit commit = walk.parseCommit(branches.get(0).getObjectId());
        assertThat(commit.getShortMessage()).isEqualTo("2nd commit");
      }
    }
  }
}
