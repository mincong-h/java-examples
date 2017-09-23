package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * git-show - Show various types of objects
 *
 * <p>
 * SYNOPSIS
 * <pre>
 * git show [options] &lt;object&gt;...
 * </pre>
 * <p>
 * Shows one or more objects (blobs, trees, tags and commits).
 *
 * <ul>
 * <li>For commits it shows the log message and textual diff. It also
 * presents the merge commit in a special format as produced by
 * <tt>git diff-tree --cc</tt>.
 * <li>For tags, it shows the tag message and the referenced objects.
 * <li>For trees, it shows the names (equivalent to
 * </tt>git ls-tree</tt> with <tt>--name-only</tt>).
 * <li>For plain blobs, it shows the plain contents.
 * </ul>
 *
 * The command takes options applicable to the <tt>git diff-tree</tt>
 * command to control how the changes the commit introduces are shown.
 *
 * @author Rüdiger Herrmann
 * @author Mincong Huang
 * @see <a href="https://stackoverflow.com/questions/46368062">Git show file in target branch in
 * JGit?</a>
 */
public class GitShowTest {

  @Rule
  public final TemporaryFolder tempFolder = new TemporaryFolder();

  private Git git;

  private Repository repo;

  @Before
  public void setUp() throws GitAPIException {
    git = Git.init().setDirectory(tempFolder.getRoot()).call();
    repo = git.getRepository();
  }

  @After
  public void tearDown() {
    repo.close();
  }

  @Test
  public void gitShowTree_master() throws Exception {
    writeFile("readme", "Hello world!");
    commitAll("Add readme file");

    ObjectId masterTreeId = git.getRepository().resolve("refs/heads/master^{tree}");

    try (TreeWalk treeWalk = TreeWalk.forPath(git.getRepository(), "readme", masterTreeId)) {
      ObjectId blobId = treeWalk.getObjectId(0);
      ObjectLoader objectLoader = loadObject(blobId);
      byte[] bytes = objectLoader.getBytes();
      assertThat(new String(bytes, StandardCharsets.UTF_8)).isEqualTo("Hello world!");
    }
  }

  private void commitAll(String message) throws Exception {
    git.add().addFilepattern(".").call();
    git.commit().setMessage(message).call();
  }

  private ObjectLoader loadObject(ObjectId objectId) throws IOException {
    try (ObjectReader objectReader = repo.newObjectReader()) {
      return objectReader.open(objectId);
    }
  }

  private void writeFile(String name, String content) throws IOException {
    File file = new File(git.getRepository().getWorkTree(), name);
    try (FileOutputStream outputStream = new FileOutputStream(file)) {
      outputStream.write(content.getBytes(StandardCharsets.UTF_8));
    }
  }

}
