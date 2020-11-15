package io.mincong.reliability.featureflag;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * @author Mincong Huang
 * @blog https://mincong.io/2020/11/11/feature-flag/
 */
class MyJobTest {

  @AfterEach
  void tearDown() {
    System.clearProperty("NEW_FEATURE_ENABLED");
  }

  @Test
  void itShouldEnableNewFeature() {
    // Given
    System.setProperty("NEW_FEATURE_ENABLED", "True");
    var set = new HashSet<String>();
    var job = new MyJob(set);

    // When
    job.run();

    // Then
    assertThat(set).containsExactly("new");
  }

  @Test
  void itShouldDisableNewFeature() {
    // Given
    System.setProperty("NEW_FEATURE_ENABLED", "False");
    var set = new HashSet<String>();
    var job = new MyJob(set);

    // When
    job.run();

    // Then
    assertThat(set).containsExactly("old");
  }

  @Test
  void itShouldFallback() {
    // Given
    var set = new HashSet<String>();
    var job = new MyJob(set);

    // When
    job.run();

    // Then
    assertThat(set).containsExactly("old");
  }
}
