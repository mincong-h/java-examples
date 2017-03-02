package io.mincongh.date.so42552801;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class DateTest {

  @Test
  public void testDateConversion() throws ParseException {
    String serverText = "2017-03-02T11:54:30.207+01:00";
    SimpleDateFormat serverFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    serverFmt.setTimeZone(TimeZone.getTimeZone("GMT+1"));
    Date timeFromServer = serverFmt.parse(serverText);

    Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT-1"));
    calendar.setTime(timeFromServer);

    assertEquals(2017, calendar.get(Calendar.YEAR));
    assertEquals(Calendar.MARCH, calendar.get(Calendar.MONTH));
    assertEquals(2, calendar.get(Calendar.DAY_OF_MONTH));
    assertEquals(9, calendar.get(Calendar.HOUR_OF_DAY));
    assertEquals(54, calendar.get(Calendar.MINUTE));
    assertEquals(30, calendar.get(Calendar.SECOND));
    assertEquals(207, calendar.get(Calendar.MILLISECOND));

    SimpleDateFormat currFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    currFmt.setTimeZone(calendar.getTimeZone());

    System.out.printf("server_timestamp  = %d, server_date  = '%s', server_str  = '%s'%n",
        timeFromServer.getTime(),
        serverFmt.format(timeFromServer),
        timeFromServer.toString());
    System.out.printf("current_timestamp = %d, current_date = '%s', current_str = '%s'%n",
        calendar.getTime().getTime(),
        currFmt.format(calendar.getTime()),
        calendar.getTime().toString());
  }

}
