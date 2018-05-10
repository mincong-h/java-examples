package io.mincong.ocpjp.i18n;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
@Ignore("OracleJDK specific")
public class FormatDateTest {

  private final long timestampInMs = 1510492455_000L;

  private final Date date = new Date(timestampInMs);

  /* Format date using DateFormat instance */

  @Test
  @Ignore("JVM specific")
  public void formatDate_default() throws Exception {
    Date date = new Date(timestampInMs);
    DateFormat fmt = DateFormat.getDateInstance();
    assertEquals("Nov 12, 2017", fmt.format(date));
  }

  @Test
  @Ignore("OracleJDK specific")
  public void formatDate_styleShort() throws Exception {
    Function<Locale, String> fmt = newDateFormatter(DateFormat.SHORT);
    assertEquals("12/11/17", fmt.apply(Locale.FRANCE));
    assertEquals("12/11/17", fmt.apply(Locale.UK));
    assertEquals("11/12/17", fmt.apply(Locale.US));
  }

  @Test
  @Ignore("OracleJDK specific")
  public void formatDate_styleMedium() throws Exception {
    Function<Locale, String> fmt = newDateFormatter(DateFormat.MEDIUM);
    assertEquals("12 nov. 2017", fmt.apply(Locale.FRANCE));
    assertEquals("12-Nov-2017", fmt.apply(Locale.UK));
    assertEquals("Nov 12, 2017", fmt.apply(Locale.US));
  }

  @Test
  @Ignore("OracleJDK specific")
  public void formatDate_styleLong() throws Exception {
    Function<Locale, String> fmt = newDateFormatter(DateFormat.LONG);
    assertEquals("12 novembre 2017", fmt.apply(Locale.FRANCE));
    assertEquals("12 November 2017", fmt.apply(Locale.UK));
    assertEquals("November 12, 2017", fmt.apply(Locale.US));
  }

  @Test
  @Ignore("OracleJDK specific")
  public void formatDate_styleFull() throws Exception {
    Function<Locale, String> fmt = newDateFormatter(DateFormat.FULL);
    assertEquals("dimanche 12 novembre 2017", fmt.apply(Locale.FRANCE));
    assertEquals("Sunday, 12 November 2017", fmt.apply(Locale.UK));
    assertEquals("Sunday, November 12, 2017", fmt.apply(Locale.US));
  }

  @Test
  @Ignore("OracleJDK specific")
  public void formatDateTime_styleShort() throws Exception {
    Function<Locale, String> fmt = newDateTimeFormatter(DateFormat.SHORT);
    assertEquals("12/11/17 13:14", fmt.apply(Locale.FRANCE));
    assertEquals("12/11/17 13:14", fmt.apply(Locale.UK));
    assertEquals("11/12/17 1:14 PM", fmt.apply(Locale.US));
  }

  @Test
  @Ignore("OracleJDK specific")
  public void formatDateTime_styleMedium() throws Exception {
    Function<Locale, String> fmt = newDateTimeFormatter(DateFormat.MEDIUM);
    assertEquals("12 nov. 2017 13:14:15", fmt.apply(Locale.FRANCE));
    assertEquals("12-Nov-2017 13:14:15", fmt.apply(Locale.UK));
    assertEquals("Nov 12, 2017 1:14:15 PM", fmt.apply(Locale.US));
  }

  @Test
  @Ignore("OracleJDK specific")
  public void formatDateTime_styleLong() throws Exception {
    Function<Locale, String> fmt = newDateTimeFormatter(DateFormat.LONG);
    assertEquals("12 novembre 2017 13:14:15 UTC", fmt.apply(Locale.FRANCE));
    assertEquals("12 November 2017 13:14:15 UTC", fmt.apply(Locale.UK));
    assertEquals("November 12, 2017 1:14:15 PM UTC", fmt.apply(Locale.US));
  }

  @Test
  @Ignore("OracleJDK specific")
  public void formatDateTime_styleFull() throws Exception {
    Function<Locale, String> fmt = newDateTimeFormatter(DateFormat.FULL);
    assertEquals("dimanche 12 novembre 2017 13 h 14 UTC", fmt.apply(Locale.FRANCE));
    assertEquals("Sunday, 12 November 2017 13:14:15 o'clock UTC", fmt.apply(Locale.UK));
    assertEquals("Sunday, November 12, 2017 1:14:15 PM UTC", fmt.apply(Locale.US));
  }

  @Test
  public void parseDate_styleLong() throws Exception {
    BiFunction<String, Locale, Long> parser = newParser(DateFormat.LONG);
    long fr = parser.apply("12 novembre 2017 13:14:15 UTC", Locale.FRANCE);
    long uk = parser.apply("12 November 2017 13:14:15 UTC", Locale.UK);
    long us = parser.apply("November 12, 2017 1:14:15 PM UTC", Locale.US);
    assertEquals(timestampInMs, fr);
    assertEquals(timestampInMs, uk);
    assertEquals(timestampInMs, us);
  }

  /**
   * @param style the int value defined by {@link DateFormat}.
   * @see DateFormat#SHORT
   * @see DateFormat#MEDIUM
   * @see DateFormat#LONG
   * @see DateFormat#FULL
   */
  private Function<Locale, String> newDateFormatter(int style) {
    return locale -> {
      DateFormat dateFormat = DateFormat.getDateInstance(style, locale);
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      return dateFormat.format(date);
    };
  }

  /**
   * @param style the int value defining style of both date-part and time-part.
   * @see DateFormat#SHORT
   * @see DateFormat#MEDIUM
   * @see DateFormat#LONG
   * @see DateFormat#FULL
   */
  private Function<Locale, String> newDateTimeFormatter(int style) {
    return locale -> {
      DateFormat dateFormat = DateFormat.getDateTimeInstance(style, style, locale);
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      return dateFormat.format(date);
    };
  }

  /**
   * @param style the int value defining style of both date-part and time-part.
   * @see DateFormat#SHORT
   * @see DateFormat#MEDIUM
   * @see DateFormat#LONG
   * @see DateFormat#FULL
   */
  private BiFunction<String, Locale, Long> newParser(int style) {
    return (d, locale) -> {
      try {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(style, style, locale);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.parse(d).getTime();
      } catch (ParseException e) {
        throw new IllegalArgumentException(e);
      }
    };
  }

  /* Format date using SimpleDateFormat */

  @Test
  public void sdf() throws Exception {
    assertEquals("2017-11-12", new SimpleDateFormat("yyyy-M-dd", Locale.US).format(date));
    assertEquals("2017-Nov-12", new SimpleDateFormat("yyyy-MMM-dd", Locale.US).format(date));
    assertEquals("2017-November-12", new SimpleDateFormat("yyyy-MMMM-dd", Locale.US).format(date));
  }

}
