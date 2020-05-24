package io.mincongh.datetime;

import java.time.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClockTest {

  /**
   * Fixed clock freezes the world at the fixed moment predefined by the {@link Clock}.
   *
   * <p>This is useful for testing: asserting any calculation which uses an instant T as reference.
   * It ensures that the tests do not depend on the current clock. For example:
   *
   * <ul>
   *   <li>Asserting the code behavior before or after the instant T. For example, testing a feature
   *       flag that will change the code behavior and will be enabled at the instant T.
   *   <li>Asserting a method which computes the result based on current instant: now.
   * </ul>
   */
  @Test
  void fixedClock() {
    // Given a clock fixed at 2020-05-24 14:00:00
    var datetime = LocalDateTime.of(2020, 5, 24, 14, 0);
    var instant = ZonedDateTime.of(datetime, ZoneId.systemDefault()).toInstant();
    var clock = Clock.fixed(instant, ZoneId.systemDefault());

    // When asking the "now" via this clock
    var now = LocalDateTime.now(clock);

    // Then "now" is not now, but 2020-05-24 14:00:00
    assertThat(now).isEqualTo(datetime);
  }
}
