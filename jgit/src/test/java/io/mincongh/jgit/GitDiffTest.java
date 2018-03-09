package io.mincongh.jgit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests git-diff command in JGit.
 *
 * @author Mincong Huang
 * @see <a href="https://git-scm.com/book/en/v2/Git-Internals-Git-Objects">10.2 Git Internals - Git
 *     Objects</a>
 */
public class GitDiffTest extends JGitTest {

  @Test
  public void name() throws Exception {
    Path txt = repo.getWorkTree().toPath().resolve("a.txt");
    Files.createFile(txt);

    RevTree oldTree = writeAndCommit(txt, "Line 1").getTree();
    RevTree newTree = writeAndCommit(txt, "Line 2").getTree();
    String oldIndex;
    String newIndex;

    try (TreeWalk treeWalk = TreeWalk.forPath(repo, "a.txt", oldTree)) {
      ObjectId blob = treeWalk.getObjectId(0);
      oldIndex = blob.abbreviate(7).name();
    }

    try (TreeWalk treeWalk = TreeWalk.forPath(repo, "a.txt", newTree)) {
      ObjectId blob = treeWalk.getObjectId(0);
      newIndex = blob.abbreviate(7).name();
    }

    String[] lines = {
      "diff --git a/a.txt b/a.txt",
      "index " + oldIndex + ".." + newIndex + " 100644",
      "--- a/a.txt",
      "+++ b/a.txt",
      "@@ -1 +1,2 @@",
      " Line 1",
      "+Line 2"
    };
    String expected = String.join(System.lineSeparator(), lines) + System.lineSeparator();

    try (ObjectReader reader = repo.newObjectReader();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      CanonicalTreeParser a = new CanonicalTreeParser(null, reader, oldTree);
      CanonicalTreeParser b = new CanonicalTreeParser(null, reader, newTree);
      git.diff().setOldTree(a).setNewTree(b).setOutputStream(out).call();

      String result = new String(out.toByteArray(), StandardCharsets.UTF_8);
      assertThat(result).isEqualTo(expected);
    }
  }

  private RevCommit writeAndCommit(Path path, String newLine) throws IOException, GitAPIException {
    Files.write(path, Collections.singletonList(newLine), StandardOpenOption.APPEND);
    return commit("Add new line " + newLine);
  }
}
