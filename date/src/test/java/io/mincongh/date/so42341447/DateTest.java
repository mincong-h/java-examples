package io.mincongh.date.so42341447;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Test;

public class DateTest {

  @Test
  public void testDate() {
    LocalDate startDate = LocalDate.parse("2017-01-28");
    LocalDate endDate = LocalDate.parse("2017-02-03");
    LocalDate d = startDate;
    StringBuilder builder = new StringBuilder();

    while (d.isBefore(endDate) || d.equals(endDate)) {
      builder.append(d.format(DateTimeFormatter.ISO_DATE)).append(" ");
      d = d.plusDays(1);
    }

    assertEquals("2017-01-28 2017-01-29 2017-01-30 2017-01-31 2017-02-01 2017-02-02 2017-02-03",
        builder.toString().trim());
  }

}
