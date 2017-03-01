package io.mincongh.date.so42542030;

import static org.junit.Assert.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class DateTest {

  @Test
  public void testDate() {
    assertTrue(isWorkingHour("2011-12-03T15:15:30-05:00"));
    assertTrue(isWorkingHour("2011-12-03T09:15:30-05:00", ZoneId.of("Europe/Paris")));
  }

  private boolean isWorkingHour(String currZoneText) {
    ZonedDateTime zonedDateTime = ZonedDateTime.parse(currZoneText);
    int currZoneHour = zonedDateTime.getHour();
    return currZoneHour >= 9 && currZoneHour <= 17;
  }

  private boolean isWorkingHour(String currZoneText, ZoneId targetZoneId) {
    ZonedDateTime sourceZonedDT = ZonedDateTime.parse(currZoneText);
    ZonedDateTime targetZonedDT = sourceZonedDT.withZoneSameInstant(targetZoneId);
    int targetZoneHour = targetZonedDT.getHour();
    return targetZoneHour >= 9 && targetZoneHour <= 17;
  }

}
