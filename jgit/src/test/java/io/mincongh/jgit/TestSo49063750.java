package io.mincongh.jgit;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 */
public class TestSo49063750 extends JGitTest {

  @Test
  public void fileMoved() throws Exception {
    Path root = tempFolder.getRoot().toPath();
    // Create 'fileA' with some content
    Files.write(root.resolve("fileA"), Arrays.asList("L1", "L2"), StandardOpenOption.CREATE);
    git.add().addFilepattern("fileA").call();
    git.commit().setMessage("Create fileA").call();

    // Move 'fileA' to 'fileB'
    Files.move(root.resolve("fileA"), root.resolve("fileB"));
    git.add().addFilepattern("fileA").call();
    git.add().addFilepattern("fileB").call();
    git.commit().setMessage("Move to fileB").call();

    try (Stream<Path> stream = Files.list(root)) {
      assertThat(stream.collect(Collectors.toList())).hasSize(2);
    }
  }
}
