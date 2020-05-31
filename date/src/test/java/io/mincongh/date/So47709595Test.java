package io.mincongh.date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
class So47709595Test {

  @Test
  void withExtraSpaces() {
    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM  yyyy  HH:mm:ss ", Locale.ENGLISH);
    assertThrows(ParseException.class, () -> sdf.parse("Thu, 7 Dec 2017 07:40:40 "));
  }

  @Test
  void withoutExtraSpaces() {
    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss ", Locale.ENGLISH);
    assertDoesNotThrow(() -> sdf.parse("Thu, 7 Dec 2017 07:40:40 "));
  }
}
