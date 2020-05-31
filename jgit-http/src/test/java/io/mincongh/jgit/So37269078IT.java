package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PostReceiveHook;
import org.eclipse.jgit.transport.PreReceiveHook;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.RemoteRefUpdate.Status;
import org.eclipse.jgit.transport.resolver.FileResolver;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Stack Overflow: Ignore Git notes getting pushed on push operation fail to remote repository
 *
 * @author Mincong Huang
 */
public class So37269078IT {

  private static final int PORT = 18080;

  @Rule public TemporaryFolder serverDir = new TemporaryFolder();
  @Rule public TemporaryFolder clientDir = new TemporaryFolder();

  private Server server;

  private int preCommands = 0;
  private int postCommands = 0;

  @Before
  public void setUp() throws Exception {
    // Git repositories setup
    File appServer = serverDir.newFolder("app.git");
    Git serverGit = Git.init().setBare(true).setDirectory(appServer).call();
    serverGit.close();

    // Set counts
    preCommands = 0;
    postCommands = 0;

    // Server setup
    GitServlet gitServlet = new GitServlet();
    gitServlet.setRepositoryResolver(new FileResolver<>(serverDir.getRoot(), true));
    gitServlet.setReceivePackFactory(new DummyReceivePackFactory());

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.addServlet(new ServletHolder(gitServlet), "/git/*");

    server = new Server(PORT);
    server.setHandler(context);
    server.start();
  }

  @After
  public void tearDown() throws Exception {
    server.stop();
  }

  @Test
  public void name() throws Exception { // NOSONAR
    List<PushResult> pushResults = new ArrayList<>();

    // Given a cloned repository
    String uri = "http://localhost:" + PORT + "/git/app.git";
    try (Git git = Git.cloneRepository().setURI(uri).setDirectory(clientDir.getRoot()).call()) {
      // When adding new commits to remote
      File readme = new File(clientDir.getRoot(), "readme");
      Files.write(readme.toPath(), Arrays.asList("L1", "L2"));
      git.commit().setAll(true).setMessage("M1").call();
      git.push().setRemote("origin").setPushAll().call().forEach(pushResults::add);
    }

    // Then the commits are pushed
    assertThat(preCommands).isEqualTo(1);
    assertThat(postCommands).isEqualTo(1);
    assertThat(pushResults).hasSize(1);
    assertThat(pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "master").getStatus())
        .isEqualTo(Status.OK);
    try (Git git = Git.open(new File(serverDir.getRoot(), "app.git"))) {
      AnyObjectId head = git.getRepository().resolve(Constants.HEAD);
      RevCommit last = git.log().setMaxCount(1).add(head).call().iterator().next();
      assertThat(last.getShortMessage()).isEqualTo("M1");
    }

    // Reset
    pushResults.clear();
    preCommands = 0;
    postCommands = 0;

    // When change master and push again (without force)
    try (Git git = Git.open(clientDir.getRoot())) {
      File readme = new File(clientDir.getRoot(), "readme");
      Files.write(readme.toPath(), Arrays.asList("L3", "L4"), StandardOpenOption.TRUNCATE_EXISTING);
      git.add().addFilepattern(".").call();
      git.commit().setAmend(true).setMessage("M1'").call();
      git.push().setRemote("origin").add("master").call().forEach(pushResults::add);
    }

    // Then the push is failed
    assertThat(pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "master").getStatus())
        .isEqualTo(Status.REJECTED_NONFASTFORWARD);
    assertThat(preCommands).as("Rejected command shouldn't pass pre-receive hook").isEqualTo(0);
    assertThat(postCommands).as("Rejected command shouldn't pass post-receive hook").isEqualTo(0);
  }

  private class MyPreReceiveHook implements PreReceiveHook {

    @Override
    public void onPreReceive(ReceivePack rp, Collection<ReceiveCommand> commands) {
      preCommands = commands.size();
    }
  }

  private class MyPostReceiveHook implements PostReceiveHook {

    @Override
    public void onPostReceive(ReceivePack rp, Collection<ReceiveCommand> commands) {
      postCommands = commands.size();
    }
  }

  private class DummyReceivePackFactory implements ReceivePackFactory<HttpServletRequest> {
    @Override
    public ReceivePack create(HttpServletRequest req, Repository db) {
      ReceivePack pack = new ReceivePack(db);
      pack.setPreReceiveHook(new MyPreReceiveHook());
      pack.setPostReceiveHook(new MyPostReceiveHook());
      pack.setRefLogIdent(new PersonIdent("Test", "test@locahost"));
      return pack;
    }
  }
}
