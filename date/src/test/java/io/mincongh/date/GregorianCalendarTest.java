package io.mincongh.date;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class GregorianCalendarTest {

  @Test
  public void testConvertGregorianCalendarToStringIso8601() {
    GregorianCalendar calendar = new GregorianCalendar(2017, Calendar.FEBRUARY, 16, 20, 22, 28);
    calendar.setTimeZone(TimeZone.getTimeZone("CET"));

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    sdf.setTimeZone(TimeZone.getTimeZone("CET"));

    Date date = calendar.getTime();
    assertEquals("2017-02-16T20:22:28.000+01:00", sdf.format(date));
  }

}
