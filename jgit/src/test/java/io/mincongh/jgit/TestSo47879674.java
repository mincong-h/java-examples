package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class TestSo47879674 extends JGitTest {

  @Test
  public void so47879674() throws Exception {
    // Create content via File IO
    File file1 = new File(repo.getWorkTree(), "file1");
    try (PrintWriter writer = new PrintWriter(file1, "UTF-8")) {
      writer.println("The 1st line: (´• ω •`)");
      writer.println("The 2nd line: (´• ω •`)");
    }

    // Create content via File NIO
    Path file2 = new File(repo.getWorkTree(), "file2").toPath();
    Files.write(file2, Collections.singletonList("(´• ω •`)"), StandardOpenOption.CREATE);

    git.add().addFilepattern(".").call();
    RevCommit commit = git.commit()
        .setMessage("Add files (´• ω •`)")
        .setAuthor("Foo", "foo@example.com")
        .call();

    // From JGit
    String contentFromJGit;
    try (TreeWalk treeWalk = TreeWalk.forPath(git.getRepository(), "file2", commit.getTree())) {
      ObjectId blobId = treeWalk.getObjectId(0);
      try (ObjectReader objectReader = repo.newObjectReader()) {
        ObjectLoader objectLoader = objectReader.open(blobId);
        byte[] bytes = objectLoader.getBytes();
        contentFromJGit = new String(bytes, StandardCharsets.UTF_8);
      }
    }

    // From File IO
    List<String> contentFromFileIO = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(file1))) {
      String line = reader.readLine();
      while (line != null) {
        contentFromFileIO.add(line);
        line = reader.readLine();
      }
    }

    // From File NIO
    List<String> contentFromFileNIO = Files.readAllLines(file2);

    assertThat(commit.getShortMessage()).isEqualTo("Add files (´• ω •`)");
    assertThat(contentFromJGit).isEqualTo("(´• ω •`)\n");
    assertThat(contentFromFileNIO).containsExactly("(´• ω •`)");
    assertThat(contentFromFileIO).containsExactly(
        "The 1st line: (´• ω •`)",
        "The 2nd line: (´• ω •`)"
    );
  }

}
