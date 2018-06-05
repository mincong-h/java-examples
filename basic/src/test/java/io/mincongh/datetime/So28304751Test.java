package io.mincongh.datetime;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Stack Overflow: How to Convert FileTime to String with DateFormat
 *
 * @author Mincong Huang
 */
@Ignore("Demo")
public class So28304751Test {
  @Rule public TemporaryFolder tempDir = new TemporaryFolder();

  @Test
  public void iso() throws Exception {
    Path path = tempDir.getRoot().toPath();
    BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
    ZonedDateTime t = attr.creationTime().toInstant().atZone(ZoneId.of("UTC"));
    String dateCreated = DateTimeFormatter.ISO_DATE_TIME.format(t);
    System.out.println(dateCreated);
  }

  @Test
  public void custom() throws Exception {
    Path path = tempDir.getRoot().toPath();
    BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
    ZonedDateTime t = attr.creationTime().toInstant().atZone(ZoneId.of("UTC"));
    String dateCreated = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(t);
    System.out.println(dateCreated);
  }
}
