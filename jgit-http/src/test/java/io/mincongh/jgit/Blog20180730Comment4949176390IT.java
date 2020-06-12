package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RemoteSetUrlCommand;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.transport.ReceiveCommand.Result;
import org.eclipse.jgit.transport.RemoteRefUpdate.Status;
import org.eclipse.jgit.transport.resolver.FileResolver;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

/**
 * Prevent operations on a branch (reference)
 * https://mincong.io/2018/07/30/jgit-protect-master-branch-on-git-server/#comment-4949176390
 *
 * @author Mincong Huang
 * @since 1.0
 */
public class Blog20180730Comment4949176390IT {
  @Rule public TemporaryFolder serverDir = new TemporaryFolder();
  @Rule public TemporaryFolder clientDir = new TemporaryFolder();

  private Server server;

  @Before
  public void setUp() throws Exception {
    // Git repositories setup
    File serverRepo = serverDir.newFolder("app.git");
    Git.init() //
        .setBare(true)
        .setDirectory(serverRepo)
        .call()
        .close();

    File clientRepo = clientDir.newFolder("app");
    CloneCommand cloneCmd =
        Git.cloneRepository() //
            .setDirectory(clientRepo)
            .setURI(serverRepo.toURI().toString());

    try (Git git = cloneCmd.call()) {
      File file = new File(clientDir.getRoot(), "readme");
      Files.write(file.toPath(), Arrays.asList("L1", "L2"));
      git.add().addFilepattern("readme").call();
      git.commit().setMessage("Add readme").call();
      git.push().setRemote("origin").add("master").call();

      RemoteSetUrlCommand cmd = git.remoteSetUrl();
      cmd.setRemoteName("origin");
      cmd.setRemoteUri(new URIish("http://localhost:8080/git/app.git"));
      cmd.call();
    }
  }

  @After
  public void tearDown() throws Exception {
    server.stop();
  }

  @Test
  public void readOnlyRef() throws Exception {
    // Given a JGit Servlet hosted by Jetty Server
    // with a read-only commit hook configured
    setupGitServer(new ReadOnlyHook("refs/heads/master"));
    server.start();

    // When pushing a commit from client Git repository
    try (Git git = Git.open(new File(clientDir.getRoot(), "app"))) {
      File file = new File(clientDir.getRoot(), "my.txt");
      Files.write(file.toPath(), Arrays.asList("Line 1", "Line 2"));
      git.add().addFilepattern("my.txt");
      git.commit().setMessage("Create my.txt").call();
      Iterable<PushResult> results = git.push().setRemote("origin").add("master").call();

      // Then the push is rejected because the branch is read-only
      int failures = 0;
      for (PushResult result : results) {
        for (RemoteRefUpdate update : result.getRemoteUpdates()) {
          assertThat(update.getStatus()).isEqualTo(Status.REJECTED_OTHER_REASON);
          failures++;
        }
      }
      assertThat(failures).isEqualTo(1);
    }
  }

  private void setupGitServer(PreReceiveHook hook) {
    // Git server setup
    GitServlet gitServlet = new GitServlet();
    gitServlet.setRepositoryResolver(new FileResolver<>(serverDir.getRoot(), true));
    ReceivePackFactory<HttpServletRequest> factory = new MyReceivePackFactory(hook);
    gitServlet.setReceivePackFactory(factory);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.addServlet(new ServletHolder(gitServlet), "/git/*");

    server = new Server(8080);
    server.setHandler(context);
  }

  private static class MyReceivePackFactory implements ReceivePackFactory<HttpServletRequest> {

    private final PreReceiveHook preReceiveHook;

    MyReceivePackFactory(PreReceiveHook preReceiveHook) {
      this.preReceiveHook = preReceiveHook;
    }

    @Override
    public ReceivePack create(HttpServletRequest req, Repository db) {
      ReceivePack pack = new ReceivePack(db);
      pack.setPreReceiveHook(preReceiveHook);
      return pack;
    }
  }

  private static class ReadOnlyHook implements PreReceiveHook {

    private final String refName;

    ReadOnlyHook(String refName) {
      this.refName = refName;
    }

    @Override
    public void onPreReceive(ReceivePack rp, Collection<ReceiveCommand> commands) {
      for (ReceiveCommand c : commands) {
        if (refName.equals(c.getRefName())) {
          /*
           * Prevent all types of operations on this reference:
           *
           * - Creation (Type.CREATE)
           * - Update (Type.UPDATE)
           * - Update non-fast-forward (Type.UPDATE_NONFASTFORWARD)
           * - Delete (Type.DELETE)
           *
           * To access to the type of the receive command:
           *
           *     c.getType()
           */
          c.setResult(Result.REJECTED_OTHER_REASON, "Read-only reference");
        }
      }
    }
  }
}
