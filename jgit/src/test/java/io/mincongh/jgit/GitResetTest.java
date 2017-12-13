package io.mincongh.jgit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.Status;
import org.junit.Test;

/**
 * "git-reset" - Reset current HEAD to the specified state
 *
 * @author Mincong Huang
 */
public class GitResetTest extends JGitTest {

  @Test
  public void resetPath() throws Exception {
    File f = new File(repo.getWorkTree(), "1.txt");
    Files.write(f.toPath(), Arrays.asList("L1", "L2"), StandardOpenOption.CREATE);
    git.add().addFilepattern(".").call();

    git.reset().addPath("1.txt").call();
    Status status = git.status().call();
    assertThat(status.getAdded()).isEmpty();
  }

  @Test
  public void resetHard() throws Exception {
    File f = new File(repo.getWorkTree(), "1.txt");
    Files.write(f.toPath(), Arrays.asList("L1", "L2"), StandardOpenOption.CREATE);
    git.add().addFilepattern(".").call();

    git.reset().setMode(ResetType.HARD).call();
    Status status = git.status().call();
    assertThat(status.getAdded()).isEmpty();
  }

  @Test
  public void resetSoft() throws Exception {
    File f = new File(repo.getWorkTree(), "1.txt");
    Files.write(f.toPath(), Arrays.asList("L1", "L2"), StandardOpenOption.CREATE);
    git.add().addFilepattern(".").call();

    git.reset().setMode(ResetType.SOFT).call();
    Status status = git.status().call();
    assertThat(status.getAdded()).containsExactly("1.txt");
  }

}
