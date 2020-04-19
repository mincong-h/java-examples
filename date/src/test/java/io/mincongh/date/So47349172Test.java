package io.mincongh.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** @author Mincong Huang */
class So47349172Test {

  @Test
  void original() {
    // 2001-01-01T00:00:00.000+00:00
    long timestamp = 978307200_000L;
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    cal.setTime(new Date(timestamp));
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Date newDate = cal.getTime();

    SimpleDateFormat u = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    u.setTimeZone(TimeZone.getTimeZone("UTC"));

    SimpleDateFormat k = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    k.setTimeZone(TimeZone.getTimeZone("Pacific/Kiritimati"));

    assertEquals(timestamp, newDate.getTime());
    assertEquals("2001-01-01T00:00:00.000+0000", u.format(newDate));
    assertEquals("2001-01-01T14:00:00.000+1400", k.format(newDate));
  }

  @Test
  void simplified() {
    // 2001-01-01T00:00:00.000+00:00
    long timestamp = 978307200_000L;
    Date newDate = new Date(timestamp);

    SimpleDateFormat u = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    u.setTimeZone(TimeZone.getTimeZone("UTC"));

    SimpleDateFormat k = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    k.setTimeZone(TimeZone.getTimeZone("Pacific/Kiritimati"));

    assertEquals("2001-01-01T00:00:00.000+0000", u.format(newDate));
    assertEquals("2001-01-01T14:00:00.000+1400", k.format(newDate));
  }
}
