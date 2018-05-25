package io.mincongh.util;

import java.util.Base64;
import org.junit.Test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class Base64Test {

  @Test
  public void decode() {
    byte[] decoded = Base64.getDecoder().decode("Zm9vOmJhcg==");
    assertThat(new String(decoded, UTF_8)).isEqualTo("foo:bar");
  }

  @Test
  public void encode() {
    byte[] encoded = Base64.getEncoder().encode("foo:bar".getBytes(UTF_8));
    assertThat(new String(encoded, UTF_8)).isEqualTo("Zm9vOmJhcg==");
  }
}
