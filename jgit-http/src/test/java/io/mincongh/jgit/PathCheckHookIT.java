package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.RemoteRefUpdate.Status;
import org.eclipse.jgit.transport.resolver.FileResolver;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Path check hook checks if the filepath is correct.
 *
 * <p>Only the path under "doc" folder is accepted.
 *
 * @author Mincong Huang
 */
public class PathCheckHookIT {

  private static final int PORT = 18080;

  private static final String URI = "http://localhost:" + PORT + "/git/app.git";

  private final List<PushResult> pushResults = new ArrayList<>();

  @Rule public TemporaryFolder serverDir = new TemporaryFolder();
  @Rule public TemporaryFolder clientDir = new TemporaryFolder();

  private Server server;

  @Before
  public void setUp() throws Exception {
    // Git repositories setup
    File appServer = serverDir.newFolder("app.git");
    Git.init().setBare(true).setDirectory(appServer).call().close();

    File appClient = clientDir.newFolder("app");
    String remoteURI = appServer.toURI().toString();
    try (Git clientGit = Git.cloneRepository().setDirectory(appClient).setURI(remoteURI).call()) {
      // master
      File file = new File(appClient, "readme");
      Files.write(file.toPath(), Arrays.asList("L1", "L2"));
      clientGit.add().addFilepattern(".").call();
      clientGit.commit().setMessage("M0").call();
      // topic
      clientGit.branchCreate().setName("topic").call();

      clientGit.push().setRemote("origin").setPushAll().call();
    }
    clientDir.delete();

    // Server setup
    GitServlet gitServlet = new GitServlet();
    gitServlet.setRepositoryResolver(new FileResolver<>(serverDir.getRoot(), true));
    gitServlet.setReceivePackFactory(new MyReceivePackFactory());

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.addServlet(new ServletHolder(gitServlet), "/git/*");

    server = new Server(PORT);
    server.setHandler(context);
    server.start();
  }

  @After
  public void tearDown() throws Exception {
    pushResults.clear();
    server.stop();
  }

  @Test
  public void update_nonDocRejected() throws Exception {
    // Given a cloned repository
    try (Git git = Git.cloneRepository().setURI(URI).setDirectory(clientDir.getRoot()).call()) {
      // When adding non-compliant commit to remote
      File readme = new File(clientDir.getRoot(), "readme");
      Files.write(readme.toPath(), Arrays.asList("L1", "L2"), StandardOpenOption.APPEND);
      git.commit().setAll(true).setMessage("M1").call();
      git.push().setRemote("origin").setPushAll().call().forEach(pushResults::add);
    }

    // Then the commit is refused
    assertThat(pushResults).hasSize(1);
    assertThat(pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "master").getStatus())
        .isEqualTo(Status.REJECTED_OTHER_REASON);
  }

  @Test
  public void update_mixedPathsRejected() throws Exception {
    // Given a cloned repository
    try (Git git = Git.cloneRepository().setURI(URI).setDirectory(clientDir.getRoot()).call()) {
      // When adding non-compliant commit to remote
      File readme = new File(clientDir.getRoot(), "readme");
      Files.write(readme.toPath(), Arrays.asList("L1", "L2"), StandardOpenOption.APPEND);
      git.commit().setAll(true).setMessage("M1").call();

      // When adding compliant commits to remote
      Path docDir = Files.createDirectory(clientDir.getRoot().toPath().resolve("doc"));

      Files.write(docDir.resolve("a"), Arrays.asList("L1", "L2"));
      git.add().addFilepattern(".").call();
      git.commit().setMessage("Add document A").call();

      git.push().setRemote("origin").setPushAll().call().forEach(pushResults::add);
    }

    // Then the commit is refused
    assertThat(pushResults).hasSize(1);
    assertThat(pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "master").getStatus())
        .isEqualTo(Status.REJECTED_OTHER_REASON);
  }

  @Test
  public void update_multiBranches() throws Exception {
    // Given a cloned repository
    try (Git git = Git.cloneRepository().setURI(URI).setDirectory(clientDir.getRoot()).call()) {
      // When adding non-compliant commit master
      File readme = new File(clientDir.getRoot(), "readme");
      Files.write(readme.toPath(), Arrays.asList("L1", "L2"), StandardOpenOption.APPEND);
      git.commit().setAll(true).setMessage("M1").call();

      // And adding compliant commits to `topic` branch
      git.checkout().setCreateBranch(true).setName("topic").setStartPoint("origin/topic").call();
      Path docDir = Files.createDirectory(clientDir.getRoot().toPath().resolve("doc"));
      Files.write(docDir.resolve("a"), Arrays.asList("L1", "L2"));
      git.add().addFilepattern(".").call();
      git.commit().setMessage("Add document A").call();

      /*
       * Note: there's only 1 push result.
       *
       * Even if there're 2 branches pushed, there's only 1 push
       * result: they belong to the same remote. Each remote has its
       * own transport instance and its own result.
       */
      git.push().setRemote("origin").setPushAll().call().forEach(pushResults::add);
    }

    // Then one update is accepted, but the other is refused
    assertThat(pushResults).hasSize(1);
    assertThat(pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "master").getStatus())
        .isEqualTo(Status.REJECTED_OTHER_REASON);
    assertThat(pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "topic").getStatus())
        .isEqualTo(Status.OK);
  }

  @Test
  public void update_docAccepted() throws Exception {
    // Given a cloned repository
    try (Git git = Git.cloneRepository().setURI(URI).setDirectory(clientDir.getRoot()).call()) {
      // When adding compliant commits to remote
      Path docDir = Files.createDirectory(clientDir.getRoot().toPath().resolve("doc"));

      Files.write(docDir.resolve("a"), Arrays.asList("L1", "L2"));
      git.add().addFilepattern(".").call();
      git.commit().setMessage("Add document A").call();

      Files.write(docDir.resolve("b"), Arrays.asList("L1", "L2"));
      git.add().addFilepattern(".").call();
      git.commit().setMessage("Add document B").call();

      git.push().setRemote("origin").setPushAll().call().forEach(pushResults::add);
    }

    // Then the commits are accepted
    assertThat(pushResults).hasSize(1);
    assertThat(pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "master").getStatus())
        .isEqualTo(Status.OK);
  }

  /** Update non-fast-forward is not permitted. */
  @Test
  public void updateNonFF_rejected() throws Exception {
    // Given a cloned repository
    try (Git git = Git.cloneRepository().setURI(URI).setDirectory(clientDir.getRoot()).call()) {
      // When pushing an amended commit
      Path docDir = Files.createDirectory(clientDir.getRoot().toPath().resolve("doc"));
      Files.write(docDir.resolve("a"), Arrays.asList("L1", "L2"));
      git.add().addFilepattern(".").call();
      git.commit().setAmend(true).setMessage("Add document A").call();

      git.push().setRemote("origin").setPushAll().call().forEach(pushResults::add);
    }

    // Then the commit is rejected
    assertThat(pushResults).hasSize(1);
    assertThat(pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "master").getStatus())
        .isEqualTo(Status.REJECTED_NONFASTFORWARD);
  }

  /** Delete branches is not permitted */
  @Test
  public void delete_rejected() throws Exception {
    // Given a cloned repository and an existing branch `topic`
    try (Git git = Git.cloneRepository().setURI(URI).setDirectory(clientDir.getRoot()).call()) {
      // When deleting branch `topic`
      RefSpec refSpec = new RefSpec().setSource(null).setDestination(Constants.R_HEADS + "topic");
      git.push().setRefSpecs(refSpec).setRemote("origin").call().forEach(pushResults::add);
    }
    // Then the push is rejected
    assertThat(pushResults).hasSize(1);
    RemoteRefUpdate rru = pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "topic");
    assertThat(rru.getMessage()).isEqualTo("deletion prohibited");
    assertThat(rru.getStatus()).isEqualTo(Status.REJECTED_OTHER_REASON);
  }

  /** Create branch is not permitted */
  @Test
  public void create_rejected() throws Exception {
    // Given a cloned repository
    try (Git git = Git.cloneRepository().setURI(URI).setDirectory(clientDir.getRoot()).call()) {
      // When creating a new branch `foo`
      git.branchCreate().setName("foo").call();
      git.push().setRemote("origin").add("foo").call().forEach(pushResults::add);
    }
    // Then the creation is rejected
    assertThat(pushResults).hasSize(1);
    RemoteRefUpdate rru = pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "foo");
    assertThat(rru.getMessage()).isEqualTo("creation prohibited");
    assertThat(rru.getStatus()).isEqualTo(Status.REJECTED_OTHER_REASON);
  }

  private class MyReceivePackFactory implements ReceivePackFactory<HttpServletRequest> {
    @Override
    public ReceivePack create(HttpServletRequest req, Repository db) {
      ReceivePack pack = new ReceivePack(db);
      pack.setPreReceiveHook(new PathCheckHook(Pattern.compile("doc/.+")));
      pack.setRefLogIdent(new PersonIdent("Test", "test@locahost"));
      return pack;
    }
  }
}
