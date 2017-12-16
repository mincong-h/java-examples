package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

/**
 * Stack-Overflow: JGit: read the content of a file at a commit in a branch
 *
 * @author Mincong Huang
 */
public class TestSo45793800 extends JGitTest {

  @Test
  public void name() throws Exception {
    Path a = new File(repo.getWorkTree(), "a.txt").toPath();
    Files.createFile(a);

    RevCommit m1 = writeAndCommit(a, "Line 1");
    RevCommit m2 = writeAndCommit(a, "Line 2");

    assertThat(getContent(m1, "a.txt")).isEqualTo("Line 1\n");
    assertThat(getContent(m2, "a.txt")).isEqualTo("Line 1\nLine 2\n");
  }

  private RevCommit writeAndCommit(Path path, String newLine) throws IOException, GitAPIException {
    Files.write(path, Collections.singletonList(newLine), StandardOpenOption.APPEND);
    return commit("Add new line " + newLine);
  }

  private String getContent(RevCommit commit, String path) throws IOException {
    try (TreeWalk treeWalk = TreeWalk.forPath(git.getRepository(), path, commit.getTree())) {
      ObjectId blobId = treeWalk.getObjectId(0);
      try (ObjectReader objectReader = repo.newObjectReader()) {
        ObjectLoader objectLoader = objectReader.open(blobId);
        byte[] bytes = objectLoader.getBytes();
        return new String(bytes, StandardCharsets.UTF_8);
      }
    }
  }

}
