package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * StackOverflow: Git fetch is deleting a new file which is recently pushed in JGit
 *
 * @author Mincong Huang
 */
public class So50666095Test {

  @Rule public final TemporaryFolder server = new TemporaryFolder();
  @Rule public final TemporaryFolder client = new TemporaryFolder();

  private String remoteURI;

  @Before
  public void setUp() throws Exception {
    File appServer = server.newFolder("app.git");
    File appClient1 = client.newFolder("app1");
    remoteURI = appServer.toURI().toString();
    Git.init().setBare(true).setDirectory(appServer).call().close();
    try (Git clientGit = Git.cloneRepository().setDirectory(appClient1).setURI(remoteURI).call()) {
      File file = new File(appClient1, "abc.txt");
      Files.write(file.toPath(), Arrays.asList("L1", "L2"));
      clientGit.add().addFilepattern(".").call();
      clientGit.commit().setMessage("M0").call();
      clientGit.push().setRemote("origin").add("master").call();
    }
  }

  @Test
  public void name() throws Exception {
    // Given client 2, cloned from server
    File app1 = new File(client.getRoot(), "app1");
    File app2 = client.newFolder("app2");
    Git.cloneRepository().setDirectory(app2).setURI(remoteURI).call().close();

    // and client 1 push some changes into server
    try (Git git = Git.open(app1)) {
      File file = new File(app1, "abc.txt");
      Files.write(file.toPath(), Arrays.asList("L3", "L4"), StandardOpenOption.APPEND);
      git.add().addFilepattern(".").call();
      git.commit().setMessage("M1").call();
      git.push().setRemote("origin").add("master").call();
    }

    // When fetching changes on client 2
    try (Git git = Git.open(app2)) {
      Repository repo = git.getRepository();
      File file = new File(app2, "abc.txt");
      Files.write(file.toPath(), Arrays.asList("L5", "L6"), StandardOpenOption.APPEND);
      git.add().addFilepattern(".").call();
      git.fetch().setRemote("origin").call();

      // Then origin/master contains changes
      List<RevCommit> history = new ArrayList<>();
      git.log().add(repo.resolve("origin/master")).setMaxCount(1).call().forEach(history::add);
      assertThat(history).flatExtracting(RevCommit::getFullMessage).containsExactly("M1");

      // but workspace does not contain changes
      List<String> lines = Files.readAllLines(new File(app2, "abc.txt").toPath());
      assertThat(lines).containsExactly("L1", "L2", "L5", "L6");
    }
  }
}
