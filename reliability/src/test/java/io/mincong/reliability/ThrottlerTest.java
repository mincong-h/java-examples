package io.mincong.reliability;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ThrottlerTest {

  @Test
  void throttle_lessMessagesThanLimit() {
    var throttler = new Throttler(3);

    var result = throttler.throttle(List.of("1", "2"));

    assertThat(result.passed).containsExactly("1", "2");
    assertThat(result.throttled).isEmpty();
  }

  @Test
  void throttle_moreMessagesThanLimit() {
    var throttler = new Throttler(3);

    var result = throttler.throttle(List.of("1", "2", "3", "4"));

    assertThat(result.passed).containsExactly("1", "2", "3");
    assertThat(result.throttled).containsExactly("4");
  }
}
