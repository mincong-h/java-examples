package io.mincongh.jgit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * How to "cat" a file in JGit?
 *
 * @author Mincong Huang
 */
public class TestSo1685228 extends JGitTest {

  @Test
  public void readFile() throws Exception {
    // Given a file with some content
    Path root = tempFolder.getRoot().toPath();
    Path docs = root.resolve("docs");
    Files.createDirectories(docs);
    Path readme = docs.resolve("README.md");
    Files.write(readme, Arrays.asList("Line1", "Line2"), StandardOpenOption.CREATE);
    System.out.println(Files.readAllLines(readme));
    commit("Add README");

    // When reading the file in `docs/README.md`
    ObjectId head = repo.resolve(Constants.HEAD);
    RevCommit lastCommit = repo.parseCommit(head);

    // Then the content is read
    assertThat(readFile(lastCommit, "docs/README.md")).isEqualTo("Line1\nLine2\n");
  }

  private String readFile(RevCommit commit , String filepath) throws IOException {
    try (TreeWalk walk = TreeWalk.forPath(repo, filepath, commit.getTree())) {
      if (walk != null) {
        byte[] bytes = repo.open(walk.getObjectId(0)).getBytes();
        return new String(bytes, StandardCharsets.UTF_8);
      } else {
        throw new IllegalArgumentException("No path found.");
      }
    }
  }
}
