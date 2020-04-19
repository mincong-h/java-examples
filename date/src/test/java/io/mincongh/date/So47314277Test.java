package io.mincongh.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Calculate number of business days between two dates.
 *
 * @author Mincong Huang
 */
class So47314277Test {

  /**
   * A set of federal holidays. Compared to iteration, using a
   * hash-based container provides a faster access for reading
   * element via hash code. Using {@link Set} avoids duplicates.
   * <p>
   * Add more dates if needed.
   */
  private static final Set<LocalDate> HOLIDAYS;

  static {
    List<LocalDate> dates = Arrays.asList(
        LocalDate.of(2017, 1, 2),
        LocalDate.of(2017, 1, 16),
        LocalDate.of(2017, 2, 20),
        LocalDate.of(2017, 5, 29),
        LocalDate.of(2017, 7, 4),
        LocalDate.of(2017, 9, 4),
        LocalDate.of(2017, 10, 9),
        LocalDate.of(2017, 11, 10),
        LocalDate.of(2017, 11, 23),
        LocalDate.of(2017, 12, 25)
    );
    HOLIDAYS = Collections.unmodifiableSet(new HashSet<>(dates));
  }

  private static int getBusinessDays(LocalDate startInclusive, LocalDate endExclusive) {
    if (startInclusive.isAfter(endExclusive)) {
      String msg = "Start date " + startInclusive
          + " must be earlier than end date " + endExclusive;
      throw new IllegalArgumentException(msg);
    }
    int businessDays = 0;
    LocalDate d = startInclusive;
    while (d.isBefore(endExclusive)) {
      DayOfWeek dw = d.getDayOfWeek();
      if (!HOLIDAYS.contains(d)
          && dw != DayOfWeek.SATURDAY
          && dw != DayOfWeek.SUNDAY) {
        businessDays++;
      }
      d = d.plusDays(1);
    }
    return businessDays;
  }

  @Test
  void illegalStartDay() {
    assertThrows(IllegalArgumentException.class, () -> getBusinessDays(LocalDate.of(2017, 1, 2), LocalDate.of(2017, 1, 1)));
  }

  @Test
  void inSameWeek() {
    int days = getBusinessDays(LocalDate.of(2017, 11, 12), LocalDate.of(2017, 11, 18));
    assertEquals(5, days);
  }

  @Test
  void inTwoWeeks() {
    int days = getBusinessDays(LocalDate.of(2017, 11, 12), LocalDate.of(2017, 11, 25));
    assertEquals(9, days);
  }

}
