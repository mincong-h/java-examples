package io.mincongh.date.so42364818;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
class DateTest {

  @Test
  void testDate() throws Exception {
    LocalDateTime d = LocalDateTime.parse("2009-06-15T13:45:30");
    assertEquals(2009, d.getYear());
    assertEquals(Month.JUNE, d.getMonth());
    assertEquals(15, d.getDayOfMonth());
    assertEquals(13, d.getHour());
    assertEquals(45, d.getMinute());
    assertEquals(30, d.getSecond());

    d.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    assertEquals("2009-06-15T13:45:30", d.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
  }
}
