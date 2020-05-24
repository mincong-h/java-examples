package io.mincongh.date;

import java.time.*;
import java.util.*;
import java.util.Map.Entry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;

/**
 * @author Mincong Huang
 * @blog https://mincong.io/2020/05/24/java-clock/
 */
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
    // Given a cache with some entries
    var instant = Instant.now();
    var cache = new Cache();
    cache.put("k1", instant);
    cache.put("k2", instant);
    cache.put("k3", instant.plusSeconds(7_200));

    // When removing expired entries from the cache
    var clock = Clock.offset(Clock.systemDefaultZone(), Cache.TTL);
    var removed = cache.clearExpired(clock);

    // Then removed entries contains exactly k1 and k2
    assertThat(removed).containsExactly(entry("k1", instant), entry("k2", instant));
  }

  static class Cache {
    static final Duration TTL = Duration.ofHours(1);
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
        if (entry.getValue().plus(TTL).isBefore(now)) {
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

  /** Which APIs accept {@link Clock} as input parameter? */
  @Test
  void commonUsage() {
    var datetime = LocalDateTime.of(2020, 5, 24, 14, 0);
    var instant = ZonedDateTime.of(datetime, ZoneOffset.UTC).toInstant();
    var clock = Clock.fixed(instant, ZoneOffset.UTC);

    assertThat(Instant.now(clock)).isEqualTo(Instant.ofEpochSecond(1590328800L));
    assertThat(LocalDate.now(clock)).isEqualTo(LocalDate.of(2020, 5, 24));
    assertThat(LocalTime.now(clock)).isEqualTo(LocalTime.of(14, 0));
    assertThat(LocalDateTime.now(clock)).isEqualTo(LocalDateTime.of(2020, 5, 24, 14, 0));
    assertThat(ZonedDateTime.now(clock))
        .isEqualTo(LocalDateTime.of(2020, 5, 24, 14, 0).atZone(ZoneOffset.UTC));
    assertThat(Year.now(clock)).isEqualTo(Year.of(2020));
    assertThat(YearMonth.now(clock)).isEqualTo(YearMonth.of(2020, 5));
    assertThat(OffsetTime.now(clock)).isEqualTo(OffsetTime.ofInstant(instant, ZoneOffset.UTC));
    assertThat(OffsetDateTime.now(clock))
        .isEqualTo(OffsetDateTime.ofInstant(instant, ZoneOffset.UTC));
  }
}
