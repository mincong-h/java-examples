package io.mincongh.jgit;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.RemoteRefUpdate.Status;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.Before;
import org.junit.Ignore;
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
  public void pushCommits() throws Exception {
    // Given a cloned repository
    CloneCommand cloneCmd =
        Git.cloneRepository()
            .setDirectory(clonedFolder.getRoot())
            .setURI(remoteDir.toUri().toString());

    // When commit and push to remote
    try (Git git = cloneCmd.call()) {
      Files.write(clonedDir.resolve("readMe"), Arrays.asList("3", "4"), StandardOpenOption.CREATE);
      git.add().addFilepattern(".").call();
      git.commit().setMessage("my commit").call();
      git.push().setRemote("origin").add("master").call();
    }

    // Then remote should receive the commit
    try (Git git = Git.open(remoteDir.toFile())) {
      List<Ref> branches = git.branchList().call();
      assertThat(branches).flatExtracting(Ref::getName).containsExactly("refs/heads/master");
      try (RevWalk walk = new RevWalk(git.getRepository())) {
        RevCommit commit = walk.parseCommit(branches.get(0).getObjectId());
        assertThat(commit.getShortMessage()).isEqualTo("my commit");
      }
    }
  }

  @Test
  public void pushTags() throws Exception {
    // Given a cloned repository
    CloneCommand cloneCmd =
        Git.cloneRepository()
            .setDirectory(clonedFolder.getRoot())
            .setURI(remoteDir.toUri().toString());

    // When create tags and push to remote
    final RevCommit commit1;
    final RevCommit commit2;
    try (Git git = cloneCmd.call()) {
      commit1 = git.commit().setAllowEmpty(true).setMessage("1st commit").call();
      commit2 = git.commit().setAllowEmpty(true).setMessage("2nd commit").call();
      git.tag().setName("1.0").setAnnotated(false).setObjectId(commit1).call();
      git.tag().setName("2.0").setAnnotated(false).setObjectId(commit2).call();
      Iterable<PushResult> results = git.push().setRemote("origin").setPushTags().call();

      // Then remote should receive the commit
      for (PushResult result : results) {
        assertStatus(result.getRemoteUpdate("refs/tags/2.0"), Status.OK);
        assertStatus(result.getRemoteUpdate("refs/tags/1.0"), Status.OK);
      }
    }

    // Then remote should receive the tags
    try (Git git = Git.open(remoteDir.toFile())) {
      List<Ref> tags = git.tagList().call();
      assertThat(tags).flatExtracting(Ref::getName).containsOnly("refs/tags/1.0", "refs/tags/2.0");
    }

    try (Git git = Git.open(clonedDir.toFile())) {
      // When forced changing the tag and push
      git.tag().setName("1.0").setForceUpdate(true).setObjectId(commit2).call();
      // Then remote should receive the commit
      Iterable<PushResult> results = git.push().setRemote("origin").setPushTags().call();
      for (PushResult result : results) {
        assertStatus(result.getRemoteUpdate("refs/tags/2.0"), Status.UP_TO_DATE);
        assertStatus(result.getRemoteUpdate("refs/tags/1.0"), Status.REJECTED_NONFASTFORWARD);
      }
    }
  }

  @Test
  public void pushSingleTag() throws Exception {
    // Given a cloned repository
    CloneCommand cloneCmd =
        Git.cloneRepository()
            .setDirectory(clonedFolder.getRoot())
            .setURI(remoteDir.toUri().toString());

    // When create tags and push to remote
    final RevCommit commit1;
    try (Git git = cloneCmd.call()) {
      commit1 = git.commit().setAllowEmpty(true).setMessage("1st commit").call();
      Ref ref1 = git.tag().setName("1.0").setAnnotated(false).setObjectId(commit1).call();
      git.tag().setName("2.0").setAnnotated(false).setObjectId(commit1).call();
      Iterable<PushResult> results = git.push().setRemote("origin").add(ref1).call();
      // Then the push result is successful
      for (PushResult result : results) {
        assertStatus(result.getRemoteUpdate("refs/tags/1.0"), Status.OK);
      }
    }

    // Then remote should receive one tag
    try (Git git = Git.open(remoteDir.toFile())) {
      List<Ref> tags = git.tagList().call();
      assertThat(tags).flatExtracting(Ref::getName).containsOnly("refs/tags/1.0");
    }
  }

  @Test
  @Ignore("Manual test in localhost")
  public void pushHttp() throws Exception {
    CloneCommand cloneCmd =
        Git.cloneRepository()
            .setDirectory(clonedFolder.getRoot())
            .setCredentialsProvider(new UsernamePasswordCredentialsProvider("user", "pwd"))
            .setURI("http://localhost/studio/junit.git");

    try (Git git = cloneCmd.call()) {
      String msg = DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());
      git.commit().setAllowEmpty(true).setMessage(msg).call();
      Iterable<PushResult> results =
          git.push()
              .setRemote("origin")
              .setPushAll()
              .setCredentialsProvider(new UsernamePasswordCredentialsProvider("user", "pwd"))
              .call();
      for (PushResult result : results) {
        System.out.println(result);
      }
    }
  }

  private void assertStatus(RemoteRefUpdate update, Status expectedStatus) {
    assertThat(update.getStatus()).isEqualTo(expectedStatus);
  }
}
