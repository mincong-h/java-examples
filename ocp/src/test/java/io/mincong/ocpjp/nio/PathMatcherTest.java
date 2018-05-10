package io.mincong.ocpjp.nio;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Mincong Huang
 */
public class PathMatcherTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private Path r;

  private Path textA, textB;

  private Path javaA, javaB;

  private Path dirA, dirB;

  private FileSystem fs;

  @Before
  public void setUp() throws Exception {
    r = temporaryFolder.getRoot().toPath();
    fs = r.getFileSystem();

    textA = Files.createFile(r.resolve("A.txt"));
    textB = Files.createFile(r.resolve("B.txt"));
    javaA = Files.createFile(r.resolve("A.java"));
    javaB = Files.createFile(r.resolve("B.java"));

    dirA = Files.createDirectory(r.resolve("A"));
    dirB = Files.createDirectory(r.resolve("B"));
  }

  @Test
  public void filterDirectory() throws Exception {
    List<Path> results = new ArrayList<>();
    Files.newDirectoryStream(r, Files::isDirectory).forEach(results::add);
    assertThat(results).containsExactlyInAnyOrder(dirA, dirB);
  }

  @Test
  public void globMatchMultipleExtensions() throws Exception {
    PathMatcher m = fs.getPathMatcher("glob:*.{txt,java}");
    assertThat(getMatches(m)).containsExactlyInAnyOrder(textA, textB, javaA, javaB);
  }

  @Test
  public void globMatchAnySingleCharacter() throws Exception {
    PathMatcher m = fs.getPathMatcher("glob:?.java");
    assertThat(getMatches(m)).containsExactlyInAnyOrder(javaA, javaB);
  }

  @Test
  public void globMatchOneCharacterFromBracket() throws Exception {
    PathMatcher m = fs.getPathMatcher("glob:[Aa].java");
    assertThat(getMatches(m)).containsExactly(javaA);
  }

  @Test
  public void globMatchOneCharacterFromRange() throws Exception {
    PathMatcher m = fs.getPathMatcher("glob:[A-Z].java");
    assertThat(getMatches(m)).containsExactlyInAnyOrder(javaA, javaB);
  }

  @Test
  public void regexMatchOneCharacterFromRange() throws Exception {
    PathMatcher m = fs.getPathMatcher("regex:\\w+.java");
    assertThat(getMatches(m)).containsExactlyInAnyOrder(javaA, javaB);
  }

  private List<Path> getMatches(PathMatcher matcher) throws IOException {
    List<Path> results = new ArrayList<>();
    Files.newDirectoryStream(r, p -> matcher.matches(p.getFileName()))
        .forEach(results::add);
    return results;
  }

}
