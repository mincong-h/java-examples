package io.mincongh.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class TestSo47709595 {

  @Test(expected = ParseException.class)
  public void withExtraSpaces() throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM  yyyy  HH:mm:ss ", Locale.ENGLISH);
    sdf.parse("Thu, 7 Dec 2017 07:40:40 ");
  }

  @Test
  public void withoutExtraSpaces() throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss ", Locale.ENGLISH);
    sdf.parse("Thu, 7 Dec 2017 07:40:40 ");
  }

}
