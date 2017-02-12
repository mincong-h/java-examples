package io.mincongh.date;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class JavaDateConversionTest {

  @Test
  public void testIso8601AndRfc822() throws ParseException {
    SimpleDateFormat rfc822 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    SimpleDateFormat iso8601S = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    SimpleDateFormat iso8601M = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXX");
    SimpleDateFormat iso8601L = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    long timestamp = 1299758070207L;

    // UTC 0 - RFC 822 vs ISO 8601
    assertEquals(timestamp, rfc822.parse("2011-03-10T11:54:30.207+0000").getTime());
    assertEquals(timestamp, iso8601S.parse("2011-03-10T11:54:30.207Z").getTime());
    assertEquals(timestamp, iso8601M.parse("2011-03-10T11:54:30.207Z").getTime());
    assertEquals(timestamp, iso8601L.parse("2011-03-10T11:54:30.207Z").getTime());

    // UTC 1 - RFC 822 vs ISO 8601
    assertEquals(timestamp - 3600_000L, rfc822.parse("2011-03-10T11:54:30.207+0100").getTime());
    assertEquals(timestamp - 3600_000L, iso8601S.parse("2011-03-10T11:54:30.207+01").getTime());
    assertEquals(timestamp - 3600_000L, iso8601M.parse("2011-03-10T11:54:30.207+0100").getTime());
    assertEquals(timestamp - 3600_000L, iso8601L.parse("2011-03-10T11:54:30.207+01:00").getTime());
  }

}
