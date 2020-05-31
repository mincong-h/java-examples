package io.mincong.immutables;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GeoPointTest {

  @Test
  void itCanGenerateFactorMethodOf() {
    // Factory method `ImmutableGeoPoint.of(double longitude, double latitude)`
    // is generated because we declare annotation `@Value.Parameter` on method
    // `longitude()` and `latitude()`.
    var point = GeoPoint.of(12.3, 45.6);
    assertThat(point.longitude()).isEqualTo(12.3);
    assertThat(point.latitude()).isEqualTo(45.6);
  }
}
