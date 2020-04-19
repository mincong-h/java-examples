package io.mincongh.datetime;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Stack Overflow: How to Convert FileTime to String with DateFormat
 *
 * @author Mincong Huang
 */
@Disabled("Demo")
class So28304751Test {

  @TempDir Path path;

  @Test
  void iso() throws Exception {
    BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
    ZonedDateTime t = attr.creationTime().toInstant().atZone(ZoneId.of("UTC"));
    String dateCreated = DateTimeFormatter.ISO_DATE_TIME.format(t);
    System.out.println(dateCreated);
  }

  @Test
  void custom() throws Exception {
    BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
    ZonedDateTime t = attr.creationTime().toInstant().atZone(ZoneId.of("UTC"));
    String dateCreated = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(t);
    System.out.println(dateCreated);
  }
}
