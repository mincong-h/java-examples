package io.mincong.ocpjp.date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * Java 8 in Action, Chapter 12: New Date and Time API
 *
 * @author Mincong Huang
 */
public class Java8DateTest {

  @Test
  public void localDate_readingValues() throws Exception {
    LocalDate d = LocalDate.of(2018, 1, 2);
    assertThat(d.getYear()).isEqualTo(2018);
    assertThat(d.getDayOfMonth()).isEqualTo(2);
    assertThat(d.getDayOfWeek()).isEqualTo(DayOfWeek.TUESDAY);
    assertThat(d.lengthOfMonth()).isEqualTo(31);
    assertThat(d.lengthOfYear()).isEqualTo(365);
  }

  @Test
  public void localDate_constructWithMonth() throws Exception {
    LocalDate d = LocalDate.of(2018, Month.JANUARY, 2);
    assertThat(d).isEqualTo(LocalDate.of(2018, 1, 2));
  }

  @Test
  @SuppressWarnings("TemporalAccessorGetChronoField") // Fail on purpose
  public void localDate_temporalField() throws Exception {
    LocalDate d = LocalDate.of(2018, 1, 2);
    assertThat(d.get(ChronoField.YEAR)).isEqualTo(2018);
    assertThat(d.get(ChronoField.DAY_OF_MONTH)).isEqualTo(2);
    assertThat(d.get(ChronoField.DAY_OF_WEEK)).isEqualTo(DayOfWeek.TUESDAY.getValue());
    try {
      d.get(ChronoField.HOUR_OF_DAY);
      fail();
    } catch (UnsupportedTemporalTypeException e) {
      // ok
    }
  }

  @Test
  public void localDate_manipulateValues() throws Exception {
    LocalDate d = LocalDate.of(2018, 1, 2);
    assertThat(d.withYear(2017)).isEqualTo(LocalDate.of(2017, 1, 2));
    assertThat(d.withMonth(2)).isEqualTo(LocalDate.of(2018, 2, 2));
    assertThat(d.withDayOfMonth(3)).isEqualTo(LocalDate.of(2018, 1, 3));
    assertThat(d.withDayOfYear(4)).isEqualTo(LocalDate.of(2018, 1, 4));
  }

  @Test
  public void localDate_temporalAdjusters() throws Exception {
    LocalDate d = LocalDate.of(2018, 1, 2);
    LocalDate f = d.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
    LocalDate l = d.with(TemporalAdjusters.lastDayOfMonth());
    assertThat(f).isEqualTo(LocalDate.of(2018, 1, 5));
    assertThat(l).isEqualTo(LocalDate.of(2018, 1, 31));
  }

  @Test
  public void localDate_parse() throws Exception {
    LocalDate d = LocalDate.parse("2018-01-02");
    assertThat(d).isEqualTo(LocalDate.of(2018, 1, 2));
  }

  @Test
  public void localTime_readingValues() throws Exception {
    LocalTime t = LocalTime.of(11, 38, 0);
    assertThat(t.getHour()).isEqualTo(11);
    assertThat(t.getMinute()).isEqualTo(38);
    assertThat(t.getSecond()).isEqualTo(0);
  }

  @Test
  public void localTime_parse() throws Exception {
    LocalTime t = LocalTime.parse("13:45:20");
    assertThat(t).isEqualTo(LocalTime.of(13, 45, 20));
  }

  @Test
  public void localDateTime_combineDateAndTime() throws Exception {
    LocalDate d = LocalDate.of(2018, 1, 2);
    LocalDateTime dt1 = d.atTime(13, 38);
    LocalDateTime dt2 = d.atTime(13, 38, 0);
    LocalDateTime dt3 = d.atTime(LocalTime.of(13, 38, 0));

    LocalDateTime expected = LocalDateTime.of(2018, 1, 2, 13, 38, 0);
    Stream.of(dt1, dt2, dt3).forEach(dt -> assertEquals(expected, dt));
  }

  @Test
  public void duration_betweenTwoLocalDateTimes() throws Exception {
    LocalDateTime d1 = LocalDateTime.of(2018, 1, 1, 0, 0, 0);
    LocalDateTime d2 = LocalDateTime.of(2018, 1, 1, 0, 12, 0);
    assertThat(Duration.between(d1, d2)).isEqualTo(Duration.ofMinutes(12));
  }

  @Test
  public void period_againstDuration() throws Exception {
    ZonedDateTime d = ZonedDateTime.of(2017, 10, 29, 0, 0, 0, 0, ZoneId.of("Europe/Paris"));
    Function<String, ZonedDateTime> parse = ZonedDateTime::parse;
    /*
     * Durations and periods differ in their treatment of daylight
     * savings time when added to `ZonedDateTime`. A `Duration` will
     * add an exact number of seconds, thus a duration of one day is
     * always exactly 24 hours. By contrast, a `Period` will add a
     * conceptual day, trying to maintain the local time.
     */
    assertThat(d.plus(Period.ofDays(1))).isEqualTo(parse.apply("2017-10-30T00:00:00+01:00"));
    assertThat(d.plus(Duration.ofDays(1))).isEqualTo(parse.apply("2017-10-29T23:00:00+01:00"));
  }

  @Test
  public void period_betweenTwoLocalDates() throws Exception {
    LocalDate d1 = LocalDate.of(2018, 1, 1);
    LocalDate d2 = LocalDate.of(2018, 1, 2);
    assertThat(Period.between(d1, d2)).isEqualTo(Period.ofDays(1));
  }

  @Test
  public void dateTimeFormatter_builtin() throws Exception {
    LocalDate d = LocalDate.of(2018, 1, 2);
    assertThat(DateTimeFormatter.BASIC_ISO_DATE.format(d)).isEqualTo("20180102");
    assertThat(DateTimeFormatter.ISO_LOCAL_DATE.format(d)).isEqualTo("2018-01-02");
    assertThat(d.format(DateTimeFormatter.ISO_LOCAL_DATE)).isEqualTo("2018-01-02");
  }

  @Test
  public void dateTimeFormatter_ofPattern() throws Exception {
    LocalDate d = LocalDate.of(2018, 1, 2);
    assertThat(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(d)).isEqualTo("2018-01-02");
  }

  @Test
  public void dateTimeFormatter_localized() throws Exception {
    LocalDate d = LocalDate.of(2018, 1, 2);
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRANCE);
    assertThat(fmt.format(d)).isEqualTo("02 janvier 2018");
  }

  @Test
  public void dateTimeFormatter_builder() throws Exception {
    LocalDate d = LocalDate.of(2018, 1, 2);
    DateTimeFormatter fmt =
        new DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .appendLiteral(" ")
            .appendText(ChronoField.MONTH_OF_YEAR)
            .appendLiteral(" ")
            .appendText(ChronoField.YEAR)
            .toFormatter(Locale.FRANCE);
    assertThat(fmt.format(d)).isEqualTo("02 janvier 2018");
  }

  /**
   * 2018-01-01T00:00:00+01:00[Europe/Paris]
   *
   * <pre>
   * +-----------+-----------+--------+
   * | LocalDate | LocalTime | ZoneId |
   * +-----------+-----------+--------+
   * |     LocalDateTime     |
   * +-----------+-----------+--------+
   * |         ZonedDateTime          |
   * +--------------------------------+
   * </pre>
   */
  @Test
  public void timezone_fromLocalDateTime() throws Exception {
    LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 0, 0, 0);
    ZonedDateTime parisDateTime = localDateTime.atZone(ZoneId.of("Europe/Paris"));
    assertThat(parisDateTime.format(DateTimeFormatter.ISO_DATE_TIME))
        .isEqualTo("2018-01-01T00:00:00+01:00[Europe/Paris]");
  }

  /**
   * Be ware that a {@code ZoneOffset} defined in this way doesn't have any Daylight Saving Time
   * management, and for this reason it isn't suggested in the majority of cases. Because a {@code
   * ZoneOffset} is also a {@code ZoneId}, you can use it as other zone ID.
   */
  @Test
  public void timezone_offset() throws Exception {
    LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 0, 0, 0);
    ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");

    OffsetDateTime t1 = localDateTime.atOffset(newYorkOffset);
    assertThat(t1.format(DateTimeFormatter.ISO_DATE_TIME)).isEqualTo("2018-01-01T00:00:00-05:00");

    OffsetDateTime t2 = OffsetDateTime.of(localDateTime, newYorkOffset);
    assertThat(t2.format(DateTimeFormatter.ISO_DATE_TIME)).isEqualTo("2018-01-01T00:00:00-05:00");
  }
}
