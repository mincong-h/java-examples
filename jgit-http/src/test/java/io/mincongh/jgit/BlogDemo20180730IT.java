package io.mincongh.jgit;

import static org.junit.Assert.assertEquals;

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
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.PreReceiveHook;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceiveCommand.Result;
import org.eclipse.jgit.transport.ReceiveCommand.Type;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.RemoteRefUpdate.Status;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.resolver.FileResolver;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class BlogDemo20180730IT {
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
      cmd.setName("origin");
      cmd.setUri(new URIish("http://localhost:8080/git/app.git"));
      cmd.call();
    }
  }

  @After
  public void tearDown() throws Exception {
    server.stop();
  }

  @Test
  public void nonFastForwardUpdate() throws Exception {
    serverSetUp(new UpdateHook1());
    server.start();
    assertFailure();
  }

  @Test
  public void config() throws Exception {
    File repo = new File(serverDir.getRoot(), "app.git");
    try (Git git = Git.open(repo)) {
      StoredConfig config = git.getRepository().getConfig();
      config.setBoolean("receive", null, "denynonfastforwards", true);
      config.save();
    }
    serverSetUp(new UpdateHook2());
    server.start();
    assertFailure();
  }

  private void serverSetUp(PreReceiveHook hook) {
    // Git server setup
    GitServlet gitServlet = new GitServlet();
    gitServlet.setRepositoryResolver(new FileResolver<>(serverDir.getRoot(), true));
    ReceivePackFactory factory = new MyReceivePackFactory(hook);
    gitServlet.setReceivePackFactory(factory);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.addServlet(new ServletHolder(gitServlet), "/git/*");

    server = new Server(8080);
    server.setHandler(context);
  }

  private void assertFailure() throws Exception {
    File repo = new File(clientDir.getRoot(), "app");
    try (Git git = Git.open(repo)) {
      git.commit().setAmend(true).setMessage("Whatever").call();
      Iterable<PushResult> results = git.push().setForce(true).call();

      int count = 0;
      for (PushResult result : results) {
        for (RemoteRefUpdate rru : result.getRemoteUpdates()) {
          System.out.println(rru);
          assertEquals(Status.REJECTED_OTHER_REASON, rru.getStatus());
          count++;
        }
      }
      assertEquals(1, count);
    }
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
      System.out.println("pack.isAllowNonFastForwards(): " + pack.isAllowNonFastForwards());
      return pack;
    }
  }

  private static class UpdateHook1 implements PreReceiveHook {

    @Override
    public void onPreReceive(ReceivePack rp, Collection<ReceiveCommand> commands) {
      for (ReceiveCommand c : commands) {
        if (c.getType() == Type.UPDATE_NONFASTFORWARD
            && "refs/heads/master".equals(c.getRefName())) {
          c.setResult(Result.REJECTED_NONFASTFORWARD);
        }
      }
    }
  }

  private static class UpdateHook2 implements PreReceiveHook {

    @Override
    public void onPreReceive(ReceivePack rp, Collection<ReceiveCommand> commands) {
      for (ReceiveCommand c : commands) {
        if (!rp.isAllowNonFastForwards()
            && c.getType() == Type.UPDATE_NONFASTFORWARD
            && "refs/heads/master".equals(c.getRefName())) {
          c.setResult(Result.REJECTED_NONFASTFORWARD);
        }
      }
    }
  }
}
