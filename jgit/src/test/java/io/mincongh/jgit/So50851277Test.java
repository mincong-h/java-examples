package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * StackOverflow: How to pull from origin using JGit on existing repository
 *
 * @author Mincong Huang
 */
public class So50851277Test {

  @Rule public final TemporaryFolder server = new TemporaryFolder();
  @Rule public final TemporaryFolder client1 = new TemporaryFolder();
  @Rule public final TemporaryFolder client2 = new TemporaryFolder();

  private String uri;

  @Before
  public void setUp() throws Exception {
    File appServer = server.newFolder("app.git");
    uri = appServer.toURI().toString();
    Git.init().setBare(true).setDirectory(appServer).call().close();
    try (Git clientGit = Git.cloneRepository().setDirectory(client1.getRoot()).setURI(uri).call()) {
      File file = client1.newFile("abc.txt");
      Files.write(file.toPath(), Arrays.asList("L1", "L2"));
      clientGit.add().addFilepattern(".").call();
      clientGit.commit().setMessage("M0").call();
      clientGit.push().setRemote("origin").add("master").call();
    }
  }

  @Test
  public void name() throws Exception {
    // Given client 2, cloned from server
    Git.cloneRepository().setDirectory(client2.getRoot()).setURI(uri).call().close();

    // and client 1 push some changes into server
    try (Git git = Git.open(client1.getRoot())) {
      File file = new File(client1.getRoot(), "abc.txt");
      Files.write(file.toPath(), Arrays.asList("L3", "L4"), StandardOpenOption.APPEND);
      git.add().addFilepattern(".").call();
      git.commit().setMessage("M1").call();
      git.push().setRemote("origin").add("master").call();
    }

    // When pulling changes on client 2
    try (Git git = Git.open(client2.getRoot())) {
      Repository repo = git.getRepository();
      PullResult result = git.pull().setRemote("origin").setRemoteBranchName("master").call();

      // Then the pull operation is successful
      // 1. pull result is successful
      assertThat(result.isSuccessful()).isTrue();

      // 2. origin/master contains changes
      List<RevCommit> history = new ArrayList<>();
      git.log().add(repo.resolve("origin/master")).setMaxCount(1).call().forEach(history::add);
      assertThat(history).flatExtracting(RevCommit::getFullMessage).containsExactly("M1");

      // 3. workspace contains changes, too
      List<String> lines = Files.readAllLines(new File(client2.getRoot(), "abc.txt").toPath());
      assertThat(lines).containsExactly("L1", "L2", "L3", "L4");
    }
  }
}
