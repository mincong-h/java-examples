package io.mincongh.datetime;

import java.time.*;
import java.util.*;
import java.util.Map.Entry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;

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

  /**
   * Offset clock adjusts the underlying clock with the specified duration added or subtracted.
   *
   * <p>This is useful for testing: asserting any behavior that requires a duration to take effect.
   * In other words, simulating something in the future or in the past. For example:
   *
   * <ul>
   *   <li>Asserting the cache invalidation after its time-to-live (TTL).
   * </ul>
   */
  @Test
  void offsetClock() {
    var ttl = Duration.ofHours(1);

    // Given a sample cache under test
    var instant = Instant.now();
    class Cache {
      final Map<String, Instant> cache = new HashMap<>();

      /**
       * Remove expired entries.
       *
       * @param clock clock to control the time
       * @return expired entries being removed
       */
      List<Entry<String, Instant>> clearExpired(Clock clock) {
        var now = Instant.now(clock);
        var it = cache.entrySet().iterator();
        var expired = new ArrayList<Map.Entry<String, Instant>>();

        while (it.hasNext()) {
          var entry = it.next();
          if (entry.getValue().plus(ttl).isBefore(now)) {
            it.remove();
            expired.add(entry);
          }
        }
        return expired;
      }

      void put(String key, Instant value) {
        cache.put(key, value);
      }
    }

    var cache = new Cache();
    cache.put("k1", instant);
    cache.put("k2", instant);
    cache.put("k3", instant.plusSeconds(7_200));

    // When removing entries from the cache
    var removed = cache.clearExpired(Clock.offset(Clock.systemDefaultZone(), ttl));

    // Then removed entries contains exactly k1 and k2
    assertThat(removed).containsExactly(entry("k1", instant), entry("k2", instant));
  }
}
