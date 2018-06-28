package io.mincongh.jgit;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UploadPack;
import org.eclipse.jgit.transport.resolver.FileResolver;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for regular expression ref filter.
 *
 * @author Mincong Huang
 */
public class RegexRefFilterIT {

  private static final int PORT = 18080;

  private static final String URI = "http://localhost:" + PORT + "/git/app.git";

  @Rule public TemporaryFolder serverDir = new TemporaryFolder();
  @Rule public TemporaryFolder clientDir = new TemporaryFolder();

  private Server server;

  @Before
  public void setUp() throws Exception {
    // Git repositories setup
    File appServer = serverDir.newFolder("app.git");
    Git.init().setBare(true).setDirectory(appServer).call().close();

    // Client setup
    File appClient = clientDir.newFolder("app");
    String remoteURI = appServer.toURI().toString();
    try (Git git = Git.cloneRepository().setDirectory(appClient).setURI(remoteURI).call()) {
      File file = new File(appClient, "readme");
      Files.write(file.toPath(), Arrays.asList("L1", "L2"));
      git.add().addFilepattern(".").call();
      git.commit().setMessage("M0").call();
      git.branchCreate().setName("public/1").call();
      git.branchCreate().setName("public/2").call();
      git.branchCreate().setName("private/1").call();
      git.branchCreate().setName("private/2").call();
      git.push().setRemote("origin").setPushAll().call();
    }
    clientDir.delete();

    // Server setup
    GitServlet gitServlet = new GitServlet();
    gitServlet.setRepositoryResolver(new FileResolver<>(serverDir.getRoot(), true));
    gitServlet.setUploadPackFactory(new MyUploadPackFactory());

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
  public void filteredRefs() throws Exception {
    try (Git git = Git.cloneRepository().setURI(URI).setDirectory(clientDir.getRoot()).call()) {
      List<Ref> refs = git.branchList().setListMode(ListMode.REMOTE).call();
      assertThat(refs)
          .flatExtracting(Ref::getName)
          .containsOnly("refs/remotes/origin/public/1", "refs/remotes/origin/public/2");
    }
  }

  private class MyUploadPackFactory implements UploadPackFactory<HttpServletRequest> {
    @Override
    public UploadPack create(HttpServletRequest req, Repository db) {
      UploadPack pack = new UploadPack(db);
      pack.setRefFilter(new RegexRefFilter(Pattern.compile("^refs/heads/public/.*$")));
      return pack;
    }
  }
}
