package io.mincongh.date;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;

/**
 * @author Mincong Huang
 * @see <a href="https://stackoverflow.com/questions/46013681">DateFormat parse() method Converting
 * invalid date to valid one in java</a>
 */
public class TestSo46013681 {

  @Test
  public void question() throws Exception {
    String input = "12-30-2017";
    DateFormat inputFormatter = new SimpleDateFormat("dd-MM-yyy");
    Date date = inputFormatter.parse(input);
    assertEquals("12-06-2019", inputFormatter.format(date));
  }

  @Test(expected = ParseException.class)
  public void answer() throws Exception {
    String input = "12-30-2017";
    DateFormat inputFormatter = new SimpleDateFormat("dd-MM-yyy");
    inputFormatter.setLenient(false);

    inputFormatter.parse(input);
  }

}
