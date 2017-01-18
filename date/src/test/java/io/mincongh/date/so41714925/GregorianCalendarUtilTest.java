package io.mincongh.date.so41714925;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

/**
 * Test changing {@code XMLgregorianCalender} default format to "yyyyMMdd", which is impossible.
 *
 * @author Mincong Huang
 * @see http://stackoverflow.com/questions/41714925
 */
public class GregorianCalendarUtilTest {

  @Test
  public void testUnmarshal() throws ParseException, DatatypeConfigurationException {
    XMLGregorianCalendar c = GregorianCalenderUtil.unmarshal("2017-01-18");
    assertEquals(2017, c.getYear());
    assertEquals(1, c.getMonth());
    assertEquals(18, c.getDay());
    assertEquals("2017-01-18", c.toXMLFormat());
    assertEquals("2017-01-18", c.toString());
  }
}
