package io.mincong.ocpjp.io;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;

/**
 * @author Mincong Huang
 */
public class FileHelperTest {

  @Rule
  public Timeout globalTimeout = Timeout.seconds(5);

  @Rule
  public TemporaryFolder temporaryDir = new TemporaryFolder();

  private File source;

  private File target;

  private final int size = 10_000;

  private final List<String> lines = IntStream.range(0, size)
      .mapToObj(i -> "Line " + i)
      .collect(Collectors.toList());

  @Before
  public void setUp() throws Exception {
    source = temporaryDir.newFile("source.txt");
    target = temporaryDir.newFile("target.txt");
    Files.write(source.toPath(), lines, UTF_8);
  }

  @Test
  public void copyByByte() throws Exception {
    FileHelper.copyByByte(source, target);
    assertThat(Files.readAllLines(target.toPath(), UTF_8)).hasSize(size);
  }

  @Test
  public void copyByBytes() throws Exception {
    FileHelper.copyByBytes(source, target);
    assertThat(Files.readAllLines(target.toPath(), UTF_8)).hasSize(size);
  }

  @Test
  public void bufferedCopyByByte() throws Exception {
    FileHelper.bufferedCopyByByte(source, target);
    assertThat(Files.readAllLines(target.toPath(), UTF_8)).hasSize(size);
  }

  @Test
  public void bufferedCopyByBytes() throws Exception {
    FileHelper.bufferedCopyByBytes(source, target);
    assertThat(Files.readAllLines(target.toPath(), UTF_8)).hasSize(size);
  }

  @Test
  public void readAndWriteObject() throws Exception {
    FileHelper.write(source, "Hello");
    String v = FileHelper.read(source, String.class);
    assertThat(v).isEqualTo("Hello");

    FileHelper.write(source, LocalDate.of(2017, 10, 7));
    LocalDate d = FileHelper.read(source, LocalDate.class);
    assertThat(d).isEqualTo(LocalDate.of(2017, 10, 7));
  }

}
