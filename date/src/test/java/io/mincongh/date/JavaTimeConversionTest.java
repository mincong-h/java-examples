package io.mincongh.date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
class JavaTimeConversionTest {

  @Test
  void testZonedDateTimeToString() {
    ZonedDateTime dateTime = LocalDate.of(2017, 1, 2).atTime(3, 4, 5).atZone(ZoneId.of("CET"));
    String dateTimeStr;

    dateTimeStr = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
    assertEquals("2017-01-02T03:04:05.000+01:00", dateTimeStr);

    dateTimeStr = dateTime.format(DateTimeFormatter.ISO_DATE);
    assertEquals("2017-01-02+01:00", dateTimeStr);

    dateTimeStr = dateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
    assertEquals("20170102+0100", dateTimeStr);
  }

  @Test
  void testLocalDateTimeToString() {
    LocalDateTime dateTime = LocalDate.of(2017, 1, 2).atTime(3, 4, 5);
    String dateTimeStr;

    dateTimeStr = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
    assertEquals("2017-01-02T03:04:05.000", dateTimeStr);

    dateTimeStr = dateTime.format(DateTimeFormatter.ISO_DATE);
    assertEquals("2017-01-02", dateTimeStr);

    dateTimeStr = dateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
    assertEquals("20170102", dateTimeStr);
  }

  @Test
  void testStringToLocalDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    LocalDateTime localDateTime;

    localDateTime = LocalDateTime.parse("2017-01-02T03:04:05.000", formatter);
    assertEquals(LocalDate.of(2017, 1, 2).atTime(3, 4, 5), localDateTime);

    localDateTime = LocalDateTime.parse("2017-01-02T03:04:05.000");
    assertEquals(LocalDate.of(2017, 1, 2).atTime(3, 4, 5), localDateTime);
  }

  @Test
  void testStringToLocalDate() {
    LocalDate localDate;

    localDate = LocalDate.parse("2017-01-02", DateTimeFormatter.ISO_DATE);
    assertEquals(LocalDate.of(2017, 1, 2), localDate);

    localDate = LocalDate.parse("20170102", DateTimeFormatter.BASIC_ISO_DATE);
    assertEquals(LocalDate.of(2017, 1, 2), localDate);

    localDate = LocalDate.parse("2017-01-02");
    assertEquals(LocalDate.of(2017, 1, 2), localDate);

    try {
      LocalDate.parse("20170102");
      fail();
    } catch (DateTimeParseException e) {
      String expectedMsg = "Text '20170102' could not be parsed at index 0";
      assertEquals(e.getMessage(), expectedMsg, e.getMessage());
    }
  }
}
