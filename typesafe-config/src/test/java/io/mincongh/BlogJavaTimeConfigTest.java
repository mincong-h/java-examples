package io.mincongh;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.typesafe.config.ConfigFactory;
import java.time.Duration;
import org.junit.jupiter.api.Test;

/**
 * @author Mincong Huang
 * @blog https://mincong.io/2020/10/25/java-time/
 */
class BlogJavaTimeConfigTest {

  @Test
  void useInteger() {
    var config = ConfigFactory.parseString("timeout: 1000 # ms");
    var timeoutInMillis = config.getLong("timeout");
    assertThat(timeoutInMillis).isEqualTo(1000L);
  }

  @Test
  void useDuration() {
    var config = ConfigFactory.parseString("timeout: 1000ms");
    var timeout = config.getDuration("timeout");
    assertThat(timeout).isEqualTo(Duration.ofMillis(1000));
  }

  @Test
  void usePeriod() {
    var config = ConfigFactory.parseString("period: 1day");
    var period = config.getPeriod("period");
    assertThat(period.getDays()).isEqualTo(1);
  }
}
