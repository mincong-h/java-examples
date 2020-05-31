package io.mincong.ocajp.chapter3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.junit.Test;

/** @author Mincong Huang */
public class JavaTimeTest {

  @Test
  public void testMonthAsEnumOrInt() {
    LocalDate localDate1 = LocalDate.of(2017, Month.FEBRUARY, 20);
    LocalDate localDate2 = LocalDate.of(2017, 2, 20);
    assertEquals("2017-02-20", localDate1.format(DateTimeFormatter.ISO_LOCAL_DATE));
    assertEquals("2017-02-20", localDate2.format(DateTimeFormatter.ISO_LOCAL_DATE));
  }

  @Test
  public void testDateSubtraction() {
    LocalDate today = LocalDate.of(2017, Month.FEBRUARY, 20);
    LocalDate yesterday = today.minusDays(1);
    assertEquals("2017-02-19", yesterday.format(DateTimeFormatter.ISO_LOCAL_DATE));
  }

  @Test
  public void testPeriod() {
    Period oneWeek = Period.ofDays(7);
    LocalDate thisFriday = LocalDate.of(2017, Month.FEBRUARY, 24);
    LocalDate lastFriday = thisFriday.minus(oneWeek);
    assertEquals("2017-02-17", lastFriday.format(DateTimeFormatter.ISO_LOCAL_DATE));

    LocalTime localTime = LocalTime.of(8, 0, 0);
    try {
      localTime.plus(oneWeek);
      fail();
    } catch (UnsupportedTemporalTypeException e) {
      assertEquals("Unsupported unit: Days", e.getMessage());
    }
  }

  @Test
  public void testParsing() {
    DateTimeFormatter f = DateTimeFormatter.ofPattern("MM dd yyyy");
    LocalDate date = LocalDate.parse("01 02 2015", f);
    assertEquals(2, date.getDayOfMonth());
    assertEquals(Month.JANUARY, date.getMonth());
    assertEquals(2015, date.getYear());

    LocalTime time = LocalTime.parse("11:22");
    assertEquals(11, time.getHour());
    assertEquals(22, time.getMinute());
  }
}
