package io.mincongh.jgit;

import java.io.File;
import java.io.IOException;
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
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class GitPreReceiveHookIT {

  private static final int PORT = 18080;

  private Server server;

  @Rule public TemporaryFolder serverDir = new TemporaryFolder();
  @Rule public TemporaryFolder clientDir = new TemporaryFolder();

  @Before
  @SuppressWarnings("unused")
  public void setUp() throws Exception {
    // Git repositories setup
    File appServer = serverDir.newFolder("app.git");
    File appClient = clientDir.newFolder("app");
    String remoteURI = appServer.toURI().toString();
    try (Git serverGit = Git.init().setBare(true).setDirectory(appServer).call();
        Git clientGit = Git.cloneRepository().setDirectory(appClient).setURI(remoteURI).call()) {
      File file = new File(appClient, "readme");
      Files.write(file.toPath(), Arrays.asList("L1", "L2"));

      clientGit.add().addFilepattern("readme").call();
      clientGit.commit().setMessage("M0").call();
      clientGit.push().setRemote("origin").add("master").call();
    }
    clientDir.delete();

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
  public void updateRef() throws Exception {
    List<PushResult> pushResults = new ArrayList<>();

    // Given a cloned repository
    String uri = "http://localhost:" + PORT + "/git/app.git";
    try (Git git = Git.cloneRepository().setURI(uri).setDirectory(clientDir.getRoot()).call()) {
      // When adding new commits to remote
      File readme = new File(clientDir.getRoot(), "readme");
      Files.write(readme.toPath(), Arrays.asList("L3", "L4"), StandardOpenOption.APPEND);
      git.commit().setAll(true).setMessage("M1").call();
      Files.write(readme.toPath(), Arrays.asList("L5", "L6"), StandardOpenOption.APPEND);
      git.commit().setAll(true).setMessage("M2").call();
      git.push().setRemote("origin").setPushAll().call().forEach(pushResults::add);
    }

    // Then the commits are pushed
    assertThat(pushResults).hasSize(1);
    assertThat(pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "master").getStatus())
        .isEqualTo(Status.OK);
    try (Git git = Git.open(new File(serverDir.getRoot(), "app.git"))) {
      AnyObjectId head = git.getRepository().resolve(Constants.HEAD);
      RevCommit last = git.log().setMaxCount(1).add(head).call().iterator().next();
      assertThat(last.getShortMessage()).isEqualTo("M2");
    }
  }

  @Test
  public void updateNonFastForwardRef() throws Exception {
    List<PushResult> pushResults = new ArrayList<>();

    // Given a cloned repository
    String uri = "http://localhost:" + PORT + "/git/app.git";
    try (Git git = Git.cloneRepository().setURI(uri).setDirectory(clientDir.getRoot()).call()) {
      // When amend to existing commit and push to remote
      File readme = new File(clientDir.getRoot(), "readme");
      Files.write(readme.toPath(), Arrays.asList("L3", "L4"), StandardOpenOption.TRUNCATE_EXISTING);
      git.commit().setAmend(true).setAll(true).setMessage("M0'").call();
      git.push().setRemote("origin").setPushAll().call().forEach(pushResults::add);
    }

    // Then the commits are pushed
    assertThat(pushResults).hasSize(1);
    assertThat(pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "master").getStatus())
        .isEqualTo(Status.REJECTED_NONFASTFORWARD);
    try (Git git = Git.open(new File(serverDir.getRoot(), "app.git"))) {
      AnyObjectId head = git.getRepository().resolve(Constants.HEAD);
      RevCommit last = git.log().setMaxCount(1).add(head).call().iterator().next();
      assertThat(last.getShortMessage()).isEqualTo("M0");
    }
  }

  @Test
  public void createRef() throws Exception {
    List<PushResult> pushResults = new ArrayList<>();

    // Given a cloned repository
    String uri = "http://localhost:" + PORT + "/git/app.git";
    try (Git git = Git.cloneRepository().setURI(uri).setDirectory(clientDir.getRoot()).call()) {
      // When creating new reference and push
      git.checkout().setCreateBranch(true).setName("topic").call();
      File readme = new File(clientDir.getRoot(), "readme");
      Files.write(readme.toPath(), Arrays.asList("L9", "L10"), StandardOpenOption.APPEND);
      git.commit().setAll(true).setMessage("T4").call();
      git.push().setRemote("origin").add("topic").call().forEach(pushResults::add);
    }

    // Then the commit is pushed
    assertThat(pushResults).hasSize(1);
    assertThat(pushResults.get(0).getRemoteUpdate(Constants.R_HEADS + "topic").getStatus())
        .isEqualTo(Status.OK);
    try (Git git = Git.open(new File(serverDir.getRoot(), "app.git"))) {
      List<Ref> refs = git.branchList().setListMode(ListMode.ALL).call();
      assertThat(refs).hasSize(2);
    }
  }

  private static class LoggingHook implements PreReceiveHook {

    private static final Logger logger = LoggerFactory.getLogger(LoggingHook.class);

    @Override
    public void onPreReceive(ReceivePack rp, Collection<ReceiveCommand> commands) {
      Repository repo = rp.getRepository();
      logger.info("advertisedRefs:");
      rp.getAdvertisedRefs().forEach((k, v) -> logger.info("k={}, v={}", k, v));

      logger.info("allCommands:");
      try (RevWalk walk = new RevWalk(repo)) {
        for (ReceiveCommand cmd : commands) {
          try {
            logger.info("{}: {} -> {}", cmd.getRefName(), cmd.getOldId(), cmd.getNewId());
            switch (cmd.getType()) {
              case UPDATE:
                walk.markStart(walk.parseCommit(cmd.getNewId()));
                walk.markUninteresting(walk.parseCommit(cmd.getOldId()));
                walk.iterator().forEachRemaining(ci -> logger.info("{}", ci));
                break;
              case CREATE:
                logger.info("{}", walk.parseCommit(cmd.getNewId()));
                break;
              case UPDATE_NONFASTFORWARD:
                logger.info("{} non fast forward", cmd.getRefName());
                break;
            }
          } catch (IOException e) {
            logger.error("Error", e.getCause());
          }
        }
      }
    }
  }

  private static class DummyReceivePackFactory implements ReceivePackFactory<HttpServletRequest> {
    @Override
    public ReceivePack create(HttpServletRequest req, Repository db) {
      ReceivePack pack = new ReceivePack(db);
      pack.setPreReceiveHook(new LoggingHook());
      pack.setRefLogIdent(new PersonIdent("Test", "test@locahost"));
      return pack;
    }
  }
}
